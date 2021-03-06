package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.BloodGroup;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class BloodGroupData {

	static Cache<BloodGroup> cache = new Cache<>();

	// Updating the cache //
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				BloodGroup bg = toBloodGroup(rs);
				cache.put(bg.getBloodGroupId(), bg);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}
	
	// Saving data //
	public static void save(BloodGroup bg) throws DBException {
		boolean isNew = bg.getBloodGroupId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO blood_group(name) values(?)");
			} else {
				sqry = DB.newQuery("UPDATE blood_group SET name = ? WHERE blood_group_id = ?");
			}

			sqry.setString(1, bg.getName());
			if (!isNew) { sqry.setInt(2, bg.getBloodGroupId()); }
			
			sqry.executeUpdate();
			if (isNew) { bg.setBloodGroupId(DB.execGetInt("SELECT MAX(blood_group_id) from blood_group")); }
			
			cache.put(bg.getBloodGroupId(), bg); // Add to
			// cache

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
cache.refreshAll();
		}

	}

	// Deleting an item //
	public static void delete(int bloodGroupId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM blood_group WHERE blood_group_id = ?");
			sqry.setInt(1, bloodGroupId);
			sqry.executeUpdate();
			cache.remove(bloodGroupId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static BloodGroup getById(int id) {
		if (id == 0) {
			return null;
		}
		BloodGroup bg  = cache.get(id);
		if (bg == null){
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group WHERE blood_group_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				bg = toBloodGroup(rs);
				cache.put(bg.getBloodGroupId(), bg);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}			
		}
		return bg;
	}

	// Get the list of items //
	public static List<BloodGroup> getList()  {
		return cache.getItemList();
	}


	private static BloodGroup toBloodGroup(ResultSet rs) throws SQLException {
		BloodGroup bg = new BloodGroup();
		bg.setBloodGroupId(rs.getInt("blood_group_id"));
		bg.setName(rs.getString("name"));
		return bg;
	}
	
	public static Cache<BloodGroup> getCache(){
		return cache;
	}

}
