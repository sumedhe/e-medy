package com.sumedhe.emedy.admission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.common.Cache;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class AdmissionTreatmentData {
	
	static Cache<AdmissionTreatment> cache = new Cache<>();
	
	
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM admission_treatment");
			ResultSet rs = sqry.executeQuery();
			cache.clear();
			while (rs.next()) {
				AdmissionTreatment d = toAdmissionTreatment(rs);
				cache.put(d.getAdmissionTreatmentId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
			cache.refreshAll();
		}
	}
	
	
	public static void save(AdmissionTreatment admissionTreatment) throws DBException{
		boolean isNew = admissionTreatment.getAdmissionTreatmentId() == 0;

		try {
			DB.open();
			PreparedStatement sqry;
			if (isNew) {
				sqry = DB.newQuery(
						"INSERT INTO admission_treatment(admission_id, treatment_id) values (?, ?)");
			} else {
				sqry = DB.newQuery(
						"UPDATE admission_treatment SET admission_id = ?, treatment_id = ? WHERE admission_treatment_id = ?");
			}

			sqry.setInt(1, admissionTreatment.getAdmissionId());
			sqry.setInt(2, admissionTreatment.getTreatmentId());
			if (!isNew) {
				sqry.setInt(4, admissionTreatment.getAdmissionId());
			}

			sqry.executeUpdate();
			if (isNew) {
				admissionTreatment.setAdmissionTreatmentId(DB.execGetInt("SELECT MAX(admission_treatment_id) from admission_treatment"));
			}
			
			cache.put(admissionTreatment.getAdmissionTreatmentId(), admissionTreatment);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}

	}
	
	public static void delete(int admissionTreatmentId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM admission_treatment WHERE admission_treatment_id = ?");
			sqry.setInt(1, admissionTreatmentId);
			sqry.executeUpdate();
			cache.remove(admissionTreatmentId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	public static AdmissionTreatment getById(int admissionTreatmentId) throws DBException {
		AdmissionTreatment at = cache.get(admissionTreatmentId);
		if (at == null){			
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM admission_treatment WHERE admission_treatment_id = ?");
				sqry.setInt(1, admissionTreatmentId);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				at = toAdmissionTreatment(rs);
				cache.put(at.getAdmissionTreatmentId(), at);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
		}
		return at;
	}
	
	public static List<AdmissionTreatment> getList()  {
		if (cache.isEmpty()){
			updateCache();
		}
		return cache.getItemList();
	}
	
	
	public static AdmissionTreatment toAdmissionTreatment(ResultSet rs) throws SQLException {
    	AdmissionTreatment at = new AdmissionTreatment();
    	at.setAdmissionTreatmentId(rs.getInt("admissionTreatmentId"));
    	at.setAdmissionId(rs.getInt("admissionId"));
    	at.setTreatmentId(rs.getInt("treatmentId"));
    	return at;
    }


	public static Cache<AdmissionTreatment> getCache(){
		return cache;
	}
}
