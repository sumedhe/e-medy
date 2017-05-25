package com.sumedhe.emedy.misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class DesignationData {

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

		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}


	public static int delete(int designationId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM designation WHERE designation_id = ?");
			sqry.setInt(1, designationId);
			return sqry.executeUpdate();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Designation findById(int id) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM designation WHERE designation_id = ?");
			sqry.setInt(1, id);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toDesignation(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static List<Designation> getList() throws DBException {
		List<Designation> designations = new ArrayList<>();
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM designation");
			ResultSet rs = sqry.executeQuery();
			while (rs.next()) {
				designations.add(toDesignation(rs));
			}
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
		return designations;
	}

	private static Designation toDesignation(ResultSet rs) throws SQLException {
		Designation d = new Designation();
		d.setDesignationId(rs.getInt("designation_id"));
		d.setName(rs.getString("name"));
		d.setWage(rs.getInt("wage"));
		return d;
	}
}
