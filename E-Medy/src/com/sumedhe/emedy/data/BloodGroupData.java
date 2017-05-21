package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sumedhe.emedy.model.BloodGroup;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class BloodGroupData {

	static Map<String, BloodGroup> cache;
	
	public static void save(BloodGroup bg) throws DBException{
		if (bg.getBloodGroupId() == 0){
			add(bg);
		} else {
			update(bg);
		}
	}

	public static void add(BloodGroup bloodgroup) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("INSERT INTO blood_group(name) values(?)");
			sqry.setString(1, bloodgroup.getName());
			sqry.executeUpdate();
			loadAll();
			bloodgroup.setBloodGroupId(DB.execGetInt("SELECT MAX(blood_group_id) from blood_group"));
		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}

	}

	public static void update(BloodGroup bloodGroup) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("UPDATE blood_group SET name = ? WHERE blood_group_id = ?");
			sqry.setString(1, bloodGroup.getName());
			sqry.executeUpdate();
		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static void delete(int bloodGroupId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM blood_group WHERE blood_group_id = ?");
			sqry.setInt(1, bloodGroupId);
			sqry.executeUpdate();
			loadAll();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static BloodGroup findById(int id) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group WHERE blood_group_id = ?");
			sqry.setInt(1, id);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toBloodGroup(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static void loadAll() throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group");
			ResultSet rs = sqry.executeQuery();
			while (rs.next()) {
				BloodGroup bg = toBloodGroup(rs);
				cache.put(bg.getName(), bg);
			}
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static BloodGroup findByName(String name) throws DBException {
		if (!cache.keySet().contains(name)) {
			loadAll();
		}		
		return cache.get(name);
	}

	public static List<BloodGroup> getList() {
		return (List<BloodGroup>) cache.values();
	}

	
	private static BloodGroup toBloodGroup(ResultSet rs) throws SQLException {
		BloodGroup bg = new BloodGroup();
		bg.setBloodGroupId(rs.getInt("blood_group_id"));
		bg.setName(rs.getString("name"));
		return bg;
	}

}
