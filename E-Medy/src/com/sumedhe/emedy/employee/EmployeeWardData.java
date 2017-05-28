package com.sumedhe.emedy.employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.common.Cache;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class EmployeeWardData {
	
	static Cache<EmployeeWard> cache = new Cache<>();
	
    
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM employee_ward");
			ResultSet rs = sqry.executeQuery();
			cache.clear();
			while (rs.next()) {
				EmployeeWard d = toEmployeeWard(rs);
				cache.put(d.getEmployeeWardId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
			cache.refreshAll();
		}
	}
	
	public static void save(EmployeeWard employeeWard) throws DBException{
		boolean isNew = employeeWard.getEmployeeWardId() == 0;

		try {
			DB.open();
			PreparedStatement sqry;
			if (isNew) {
				sqry = DB.newQuery(
						"INSERT INTO employee_ward(employee_id, ward_id) values (?, ?)");
			} else {
				sqry = DB.newQuery(
						"UPDATE employee_ward SET employee_id = ?, ward_id = ? WHERE employee_ward_id = ?");
			}

			sqry.setInt(1, employeeWard.getEmployeeId());
			sqry.setInt(2, employeeWard.getWardId());
			if (!isNew) {
				sqry.setInt(3, employeeWard.getEmployeeId());
			}

			sqry.executeUpdate();
			if (isNew) {
				employeeWard.setEmployeeWardId(DB.execGetInt("SELECT MAX(employee_ward_id) from employee_ward"));
			}
			
			cache.put(employeeWard.getEmployeeWardId(), employeeWard);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}

	}
	
	public static void delete(int employeeWardId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM employee_ward WHERE employee_ward_id = ?");
			sqry.setInt(1, employeeWardId);
			sqry.executeUpdate();
			cache.remove(employeeWardId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	public static EmployeeWard getById(int employeeWardId) throws DBException {
		EmployeeWard ew = cache.get(employeeWardId);
		if (ew == null){
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM employee_ward WHERE employee_id = ?");
				sqry.setInt(1, employeeWardId);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				ew = toEmployeeWard(rs);
				cache.put(ew.getEmployeeWardId(), ew);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}			
		}
		return ew;
	}
	
	public static List<EmployeeWard> getList()  {
		if (cache.isEmpty()){
			updateCache();
		}
		return cache.getItemList();
	}
	
	
	public static EmployeeWard toEmployeeWard(ResultSet rs) throws SQLException {
    	EmployeeWard at = new EmployeeWard();
    	at.setEmployeeWardId(rs.getInt("employeeWardId"));
    	at.setEmployeeId(rs.getInt("employeeId"));
    	at.setWardId(rs.getInt("wardId"));
    	return at;
    }
	
	public static Cache<EmployeeWard> getCache(){
		return cache;
	}
}
