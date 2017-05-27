package com.sumedhe.emedy.patient;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class PatientData {

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

		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}

	}


	public static int delete(int patientId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM patient WHERE patient_id = ?");
			sqry.setInt(1, patientId);
			Global.log(sqry.toString());
			return sqry.executeUpdate();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Patient findById(int id) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM patient WHERE patient_id = ?");
			sqry.setInt(1, id);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toPatient(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static List<Patient> getList(String keyword) throws DBException {
		List<Patient> patients = new ArrayList<>();
		String qry = "SELECT * FROM patient";
		if (!keyword.equals("")){
			qry += String.format(" WHERE first_name LIKE '%s%%' OR last_name LIKE '%s%%' OR nic LIKE '%s%%'", keyword, keyword, keyword);
		}
		
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery(qry);			
			ResultSet rs = sqry.executeQuery();
			while (rs.next()) {
				patients.add(toPatient(rs));
			}
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
		return patients;
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
}
