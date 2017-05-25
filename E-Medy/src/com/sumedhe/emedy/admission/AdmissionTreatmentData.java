package com.sumedhe.emedy.admission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class AdmissionTreatmentData {
    
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

		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}

	}
	
	public static int delete(int admissionTreatmentId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM admission_treatment WHERE admission_treatment_id = ?");
			sqry.setInt(1, admissionTreatmentId);
			return sqry.executeUpdate();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	public static AdmissionTreatment getListByAdmissionId(int admissionId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM admission_treatment WHERE admission_id = ?");
			sqry.setInt(1, admissionId);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toAdmissionTreatment(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	
	public static AdmissionTreatment toAdmissionTreatment(ResultSet rs) throws SQLException {
    	AdmissionTreatment at = new AdmissionTreatment();
    	at.setAdmissionTreatmentId(rs.getInt("admissionTreatmentId"));
    	at.setAdmissionId(rs.getInt("admissionId"));
    	at.setTreatmentId(rs.getInt("treatmentId"));
    	return at;
    }
}
