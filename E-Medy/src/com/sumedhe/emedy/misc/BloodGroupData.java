package com.sumedhe.emedy.misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class BloodGroupData {

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
			getList();
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

	public static List<BloodGroup> getList() throws DBException {
		List<BloodGroup> bloodGroups = new ArrayList<>();
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM blood_group");
			ResultSet rs = sqry.executeQuery();
			while (rs.next()) {
				bloodGroups.add(toBloodGroup(rs));
			}
			return bloodGroups;
			
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}


	private static BloodGroup toBloodGroup(ResultSet rs) throws SQLException {
		BloodGroup bg = new BloodGroup();
		bg.setBloodGroupId(rs.getInt("blood_group_id"));
		bg.setName(rs.getString("name"));
		return bg;
	}

}
