package com.sumedhe.emedy.misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.common.Cache;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class TreatmentData {
	
	static Cache<Treatment> cache = new Cache<>();
	
	
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM treatment");
			ResultSet rs = sqry.executeQuery();
			cache.clear();
			while (rs.next()) {
				Treatment d = toTreatment(rs);
				cache.put(d.getTreatmentId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
			cache.refreshAll();
		}
	}

	public static void save(Treatment treatment) throws DBException {
		boolean isNew = treatment.getTreatmentId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO treatment(name, fee) values(?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE treatment SET name = ?, fee = ? WHERE treatment_id = ?");
			}

			sqry.setString(1, treatment.getName());
			sqry.setDouble(2, treatment.getFee());
			if (!isNew) {
				sqry.setInt(3, treatment.getTreatmentId());
			}

			sqry.executeUpdate();
			if (isNew) {
				treatment.setTreatmentId(DB.execGetInt("SELECT MAX(treatment_id) from treatment"));
			}
			
			cache.put(treatment.getTreatmentId(), treatment);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}


	public static void delete(int treatmentId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM treatment WHERE treatment_id = ?");
			sqry.setInt(1, treatmentId);
			sqry.executeUpdate();
			cache.remove(treatmentId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Treatment getById(int id){
		Treatment t = cache.get(id);
		if (t == null){
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM treatment WHERE treatment_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				t = toTreatment(rs);
				cache.put(t.getTreatmentId(), t);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
			
		}
		return t;
	}

	public static List<Treatment> getList()  {

		return cache.getItemList();
	}

	private static Treatment toTreatment(ResultSet rs) throws SQLException {
		Treatment w = new Treatment();
		w.setTreatmentId(rs.getInt("treatment_id"));
		w.setName(rs.getString("name"));
		w.setFee(rs.getDouble("fee"));
		return w;
	}

	public static Cache<Treatment> getCache(){
		return cache;
	}
}
