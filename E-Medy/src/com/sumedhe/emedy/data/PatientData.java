package com.sumedhe.emedy.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Patient;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class PatientData {

	public static Cache<Patient> cache = new Cache<>();

	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM patient");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Patient d = toPatient(rs);
				cache.put(d.getPatientId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}

	public static void save(Patient patient) throws DBException {
		boolean isNew = patient.getPatientId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery(
						"INSERT INTO patient(first_name, last_name, nic, dob, gender, address, phone, mobile, blood_group_id, consultant_id, registered_on) values(?,?,?,?,?,?,?,?,?,?,?)");
			} else {
				sqry = DB.newQuery(
						"UPDATE patient SET first_name = ?, last_name = ?, nic = ?, dob = ?, gender = ?, address = ?, phone = ?, mobile = ?, blood_group_id = ?, consultant_id = ?, registered_on = ? WHERE patient_id = ?");
			}

			sqry.setString(1, patient.getFirstName());
			sqry.setString(2, patient.getLastName());
			sqry.setString(3, patient.getNic());
			sqry.setDate(4, (Date) patient.getDob());
			sqry.setString(5, patient.getGender() == Gender.Male ? "M" : "F");
			sqry.setString(6, patient.getAddress());
			sqry.setString(7, patient.getPhone());
			sqry.setString(8, patient.getMobile());
			sqry.setInt(9, patient.getBloodGroupId());
			sqry.setInt(10, patient.getConsultantId());
			sqry.setDate(11, (Date) patient.getRegisteredOn());
			if (!isNew) {
				sqry.setInt(12, patient.getPatientId());
			}
			Global.log(sqry.toString());

			sqry.executeUpdate();
			if (isNew) {
				patient.setPatientId(DB.execGetInt("SELECT MAX(patient_id) from patient"));
			}

			cache.put(patient.getPatientId(), patient);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}

	}

	public static void delete(int patientId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM patient WHERE patient_id = ?");
			sqry.setInt(1, patientId);
			Global.log(sqry.toString());
			sqry.executeUpdate();
			cache.remove(patientId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Patient getById(int id) {
		if (id == 0) {
			return null;
		}
		Patient p = cache.get(id);
		if (p == null) {
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM patient WHERE patient_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				p = toPatient(rs);
				cache.put(p.getPatientId(), p);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
		}
		return p;
	}

	public static List<Patient> getList() {
		return cache.getItemList();
	}

	public static List<Patient> getBySearch(String keyword) throws DBException {
		return cache.getStream().filter(
				x -> String.format(" %s %s", x.getName(), x.getNic()).toLowerCase().contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
	}

	private static Patient toPatient(ResultSet rs) throws SQLException {
		Patient p = new Patient();
		p.setPatientId(rs.getInt("patient_id"));
		p.setFirstName(rs.getString("first_name"));
		p.setLastName(rs.getString("last_name"));
		p.setNic(rs.getString("nic"));
		p.setDob(rs.getDate("dob"));
		p.setGender(rs.getString("gender").charAt(0) == 'M' ? Gender.Male : Gender.Female);
		p.setAddress(rs.getString("address"));
		p.setPhone(rs.getString("phone"));
		p.setMobile(rs.getString("mobile"));
		p.setBloodGroupId(rs.getInt("blood_group_id"));
		p.setConsultantId(rs.getInt("consultant_id"));
		p.setRegisteredOn(rs.getDate("registered_on"));
		return p;
	}

	public static Cache<Patient> getCache() {
		return cache;
	}
}
