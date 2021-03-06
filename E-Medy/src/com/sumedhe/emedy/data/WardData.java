package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Ward;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class WardData {

	static Cache<Ward> cache = new Cache<>();

	// Updating the cache //
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM ward");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Ward d = toWard(rs);
				cache.put(d.getWardId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}

	// Saving data //
	public static void save(Ward ward) throws DBException {
		boolean isNew = ward.getWardId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO ward(name, ward_no, max_patients) values(?, ?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE ward SET name = ?, ward_no = ?, max_patients = ? WHERE ward_id = ?");
			}

			sqry.setString(1, ward.getName());
			sqry.setInt(2, ward.getMaxPatients());
			sqry.setInt(3, ward.getWardNo());
			if (!isNew) {
				sqry.setInt(4, ward.getWardId());
			}

			sqry.executeUpdate();
			if (isNew) {
				ward.setWardId(DB.execGetInt("SELECT MAX(ward_id) from ward"));
			}

			cache.put(ward.getWardId(), ward);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
			cache.refreshAll();
		}

	}

	// Deleting an item //
	public static void delete(int wardId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM ward WHERE ward_id = ?");
			sqry.setInt(1, wardId);
			sqry.executeUpdate();
			cache.remove(wardId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Ward getById(int id) {
		if (id == 0) {
			return null;
		}
		Ward w = cache.get(id);
		if (w == null) {
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM ward WHERE ward_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				w = toWard(rs);
				cache.put(w.getWardId(), w);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}

		}
		return w;
	}
	

	// Get the list of items //
	public static List<Ward> getList() {
		return cache.getItemList();
	}

	// Get the list of items //
	public static List<Ward> getBySearch(String keyword) throws DBException {
		return cache.getStream()
				.filter(x -> String.format("%s %s", x.getName().toLowerCase(), Integer.toString(x.getWardNo()))
						.contains(keyword.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public static Ward getByWardNo(int wardNo){
		List<Ward> list = cache.getStream().filter(x -> x.getWardNo() == wardNo).collect(Collectors.toList());
		return list.size() != 0 ? list.get(0) : null;
	}
	

	private static Ward toWard(ResultSet rs) throws SQLException {
		Ward w = new Ward();
		w.setWardId(rs.getInt("ward_id"));
		w.setName(rs.getString("name"));
		w.setWardNo(rs.getInt("ward_no"));
		w.setMaxPatients(rs.getInt("max_patients"));
		return w;
	}

	public static Cache<Ward> getCache() {
		return cache;
	}
}
