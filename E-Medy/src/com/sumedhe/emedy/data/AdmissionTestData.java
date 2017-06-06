package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.AdmissionTest;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class AdmissionTestData {
	
	static Cache<AdmissionTest> cache = new Cache<>();
	
    
	// Updating the cache //
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM admission_test");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				AdmissionTest d = toAdmissionTest(rs);
				cache.put(d.getAdmissionTestId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}
	
	// Saving data //
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
			
			cache.put(admissionTest.getAdmissionTestId(), admissionTest);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}

	}
	
	// Deleting an item //
	public static void delete(int admissionTestId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM admission_test WHERE admission_test_id = ?");
			sqry.setInt(1, admissionTestId);
			sqry.executeUpdate();
			cache.remove(admissionTestId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	public static AdmissionTest getById(int id) {
		if (id == 0) {
			return null;
		}
		AdmissionTest t = cache.get(id);
		if (t == null){
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM admission_test WHERE admission_test_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				t = toAdmissionTest(rs);
				cache.put(t.getAdmissionTestId(), t);				
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}			
		}
		return t;
	}
	
	// Get the list of items //
	public static List<AdmissionTest> getList()  {
		return cache.getItemList();
	}
	
	// Get the list of items //
	public static List<AdmissionTest> getByAdmissionId(int admissionId) {
		return cache.getStream().filter(x -> x.getAdmissionId() == admissionId).collect(Collectors.toList());
	}
	
	
	public static AdmissionTest toAdmissionTest(ResultSet rs) throws SQLException {
    	AdmissionTest at = new AdmissionTest();
    	at.setAdmissionTestId(rs.getInt("admission_test_id"));
    	at.setAdmissionId(rs.getInt("admission_id"));
    	at.setTestId(rs.getInt("test_id"));
    	at.setResult(rs.getString("result"));
    	return at;
    }


	public static Cache<AdmissionTest> getCache(){
		return cache;
	}
}
