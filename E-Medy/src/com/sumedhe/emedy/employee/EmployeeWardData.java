package com.sumedhe.emedy.employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class EmployeeWardData {
    
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

		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}

	}
	
	public static int delete(int employeeWardId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM employee_ward WHERE employee_ward_id = ?");
			sqry.setInt(1, employeeWardId);
			return sqry.executeUpdate();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	public static EmployeeWard getListByEmployeeId(int employeeId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM employee_ward WHERE employee_id = ?");
			sqry.setInt(1, employeeId);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toEmployeeWard(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}
	
	
	public static EmployeeWard toEmployeeWard(ResultSet rs) throws SQLException {
    	EmployeeWard at = new EmployeeWard();
    	at.setEmployeeWardId(rs.getInt("employeeWardId"));
    	at.setEmployeeId(rs.getInt("employeeId"));
    	at.setWardId(rs.getInt("wardId"));
    	return at;
    }
}
