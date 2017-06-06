package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Admission;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class AdmissionData {
	
	static Cache<Admission> cache = new Cache<>();
	
	// Updating the cache //
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM admission");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Admission d = toAdmission(rs);
				cache.put(d.getAdmissionId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}
	
	public static void save(Admission admission) throws DBException {
		boolean isNew = admission.getAdmissionId() == 0;

		try {
			DB.open();
			PreparedStatement sqry;
			if (isNew) {
				sqry = DB.newQuery(
						"INSERT INTO admission(patient_id, ward_id, recommended_by, confirmed_doctor_id, custodian_name, custodian_nic, custodian_phone, admitted_on, is_discharged, discharged_on, is_paid) values(?,?,?,?,?,?,?,?,?,?,?)");
			} else {
				sqry = DB.newQuery(
						"UPDATE admission SET patient_id = ?, ward_id = ?, recommended_by = ?, confirmed_doctor_id = ?, custodian_name = ?, custodian_nic = ?, custodian_phone = ?, admitted_on = ?, is_discharged = ?, discharged_on = ?, is_paid = ? WHERE admission_id = ?");
			}

			sqry.setInt(1, admission.getPatient().getPatientId());
			sqry.setInt(2, admission.getWard().getWardId());
			sqry.setString(3, admission.getRecommendedBy());
			sqry.setInt(4, admission.getConfirmedDoctor().getDoctorId());
			sqry.setString(5, admission.getCustodianName());
			sqry.setString(6, admission.getCustodianNIC());
			sqry.setString(7, admission.getCustodianPhone());
			sqry.setDate(8, admission.getAdmittedOn());
			sqry.setBoolean(9, admission.getIsDischarged());
			sqry.setDate(10, admission.getDischargedOn());
			sqry.setBoolean(11, admission.getIsPaid());
			
			if (!isNew) {
				sqry.setInt(12, admission.getAdmissionId());
			}

			sqry.executeUpdate();
			if (isNew) {
				admission.setAdmissionId(DB.execGetInt("SELECT MAX(admission_id) from admission"));
			}
			
			cache.put(admission.getAdmissionId(), admission);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}

	}


	public static void delete(int admissionId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM admission WHERE admission_id = ?");
			sqry.setInt(1, admissionId);
			sqry.executeUpdate();
			cache.remove(admissionId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Admission getById(int id) {
		if (id == 0) {
			return null;
		}
		Admission a = cache.get(id);
		if (a == null){
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM admission WHERE admission_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				a = toAdmission(rs);
				cache.put(a.getAdmissionId(), a);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}			
		}
		return a;
	}

	public static List<Admission> getList()  {
		return cache.getItemList();
	}
	
	public static List<Admission> getBySearch(String keyword) throws DBException {
		return cache.getStream().filter(
				x -> String.format(" %s %s %s", x.getPatient(), x.getWard(), x.getCustodianName()).toLowerCase().contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
	}

	
	private static Admission toAdmission(ResultSet rs) throws SQLException {
		Admission a = new Admission();
		a.setAdmissionId(rs.getInt("admission_id"));
		a.setPatientId(rs.getInt("patient_id"));
		a.setWardId(rs.getInt("ward_id"));
		a.setRecommendedBy(rs.getString("recommended_by"));
		a.setConfirmedDoctorId(rs.getInt("confirmed_doctor_id"));
		a.setCustodianName(rs.getString("custodian_name"));
		a.setCustodianNIC(rs.getString("custodian_nic"));
		a.setCustodianPhone(rs.getString("custodian_phone"));
		a.setAdmittedOn(rs.getDate("admitted_on"));
		a.setDischarged(rs.getBoolean("is_discharged"));
		a.setDischargedOn(rs.getDate("discharged_on"));
		a.setIsPaid(rs.getBoolean("is_paid"));
		return a;
	}


	public static Cache<Admission> getCache(){
		return cache;
	}
}
