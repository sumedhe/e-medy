package com.sumedhe.emedy.misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.common.Cache;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class DesignationData {

	static Cache<Designation> cache = new Cache<>();
	
	
	public static void updateCache()  {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM designation");
			ResultSet rs = sqry.executeQuery();
			cache.clear();
			while (rs.next()) {
				Designation d = toDesignation(rs);
				cache.put(d.getDesignationId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
			cache.refreshAll();
		}
	}

	public static void save(Designation designation) throws DBException {
		boolean isNew = designation.getDesignationId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO designation(name, wage) values(?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE designation SET name = ?, wage = ? WHERE designation_id = ?");
			}

			sqry.setString(1, designation.getName());
			sqry.setDouble(2, designation.getWage());
			if (!isNew) {
				sqry.setInt(3, designation.getDesignationId());
			}

			sqry.executeUpdate();
			if (isNew) {
				designation.setDesignationId(DB.execGetInt("SELECT MAX(designation_id) from designation"));
			}

			cache.put(designation.getDesignationId(), designation); // Add to
																	// cache

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static void delete(int designationId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM designation WHERE designation_id = ?");
			sqry.setInt(1, designationId);
			sqry.executeUpdate();
			cache.remove(designationId); // Remove from cache
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Designation getById(int id){
		Designation d = cache.get(id);
		if (d == null) {
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM designation WHERE designation_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				d = toDesignation(rs);
				cache.put(d.getDesignationId(), d);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
		}
		return d;
	}

	public static List<Designation> getList() {
		if (cache.isEmpty()){
			updateCache();
		}
		return cache.getItemList();
	}

	private static Designation toDesignation(ResultSet rs) throws SQLException {
		Designation d = new Designation();
		d.setDesignationId(rs.getInt("designation_id"));
		d.setName(rs.getString("name"));
		d.setWage(rs.getInt("wage"));
		return d;
	}

	public static Cache<Designation> getCache(){
		return cache;
	}
	
}
