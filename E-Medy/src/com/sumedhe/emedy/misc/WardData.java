package com.sumedhe.emedy.misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.common.Cache;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class WardData {
	
	static Cache<Ward> cache = new Cache<>();

	
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM ward");
			ResultSet rs = sqry.executeQuery();
			cache.clear();
			while (rs.next()) {
				Ward d = toWard(rs);
				cache.put(d.getWardId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
			cache.refreshAll();
		}
	}
	
	public static void save(Ward ward) throws DBException {
		boolean isNew = ward.getWardId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO ward(name, max_patients) values(?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE ward SET name = ?, max_patients = ? WHERE ward_id = ?");
			}

			sqry.setString(1, ward.getName());
			sqry.setInt(2, ward.getMaxPatients());
			if (!isNew) {
				sqry.setInt(3, ward.getWardId());
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
		}

	}

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

	public static Ward getById(int id){
		Ward w = cache.get(id);
		if (w == null){
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

	public static List<Ward> getList()  {
		if (cache.isEmpty()){
			updateCache();
		}
		return cache.getItemList();
	}

	private static Ward toWard(ResultSet rs) throws SQLException {
		Ward w = new Ward();
		w.setWardId(rs.getInt("ward_id"));
		w.setName(rs.getString("name"));
		w.setMaxPatients(rs.getInt("max_patients"));
		return w;
	}

	public static Cache<Ward> getCache(){
		return cache;
	}
}
