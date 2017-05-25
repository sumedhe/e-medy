package com.sumedhe.emedy.admission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class AdmissionTestData {
    
	public static void save(AdmissionTest admissionTest) throws DBException{
		boolean isNew = admissionTest.getAdmissionTestId() == 0;

		try {
			DB.open();
			PreparedStatement sqry;
			if (isNew) {
				sqry = DB.newQuery(
						"INSERT INTO admission_test(admission_id, test_id, result) values (?, ?, ?)");
			} else {
				sqry = DB.newQuery(
						"UPDATE admission_test SET admission_id = ?, test_id = ?, result = ? WHERE admission_test_id = ?");
			}

			sqry.setInt(1, admissionTest.getAdmissionId());
			sqry.setInt(2, admissionTest.getTestId());
			sqry.setString(3, admissionTest.getResult());
			if (!isNew) {
				sqry.setInt(4, admissionTest.getAdmissionId());
			}

			sqry.executeUpdate();
			if (isNew) {
				admissionTest.setAdmissionTestId(DB.execGetInt("SELECT MAX(admission_test_id) from admission_test"));
			}

		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}

	}
	
	public static int delete(int admissionTestId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM admission_test WHERE admission_test_id = ?");
			sqry.setInt(1, admissionTestId);
			return sqry.executeUpdate();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	public static AdmissionTest getListByAdmissionId(int admissionId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM admission_test WHERE admission_id = ?");
			sqry.setInt(1, admissionId);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toAdmissionTest(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	
	public static AdmissionTest toAdmissionTest(ResultSet rs) throws SQLException {
    	AdmissionTest at = new AdmissionTest();
    	at.setAdmissionTestId(rs.getInt("admissionTestId"));
    	at.setAdmissionId(rs.getInt("admissionId"));
    	at.setTestId(rs.getInt("testId"));
    	at.setResult(rs.getString("result"));
    	return at;
    }
}
