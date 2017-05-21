package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.model.Ward;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;



public class WardData {
	
	public static void save(Ward ward) throws DBException{
		if (ward.getWardId() == 0){
			add(ward);
		} else {
			update(ward);
		}
	}


    public static void add(Ward ward) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("INSERT INTO ward(name, max_patients) values(?, ?)");
            sqry.setString(1, ward.getName());
            sqry.setInt(2, ward.getMaxPatients());
            sqry.executeUpdate();
            ward.setWardId(DB.execGetInt("SELECT MAX(ward_id) from ward"));
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }

    }

    public static void update(Ward ward) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("UPDATE ward SET name = ?, max_patients = ? WHERE ward_id = ?");
            sqry.setString(1, ward.getName());
            sqry.setInt(2, ward.getMaxPatients());
            sqry.setInt(3, ward.getWardId());
            sqry.executeUpdate();
        } catch (DBException | SQLException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static int delete(int wardId) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("DELETE FROM ward WHERE ward_id = ?");
            sqry.setInt(1, wardId);
            return sqry.executeUpdate();
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static Ward findById(int id) throws DBException {
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM ward WHERE ward_id = ?");
            sqry.setInt(1, id);
            ResultSet rs = sqry.executeQuery();
            rs.next();
            return toWard(rs);
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
    }

    public static List<Ward> getList() throws DBException {
        List<Ward> wards = new ArrayList<>();
        try {
            DB.open();
            PreparedStatement sqry = DB.newQuery("SELECT * FROM ward");
            ResultSet rs = sqry.executeQuery();
            while (rs.next()) {
                wards.add(toWard(rs));
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("Error: " + ex.getMessage());
        } finally {
            DB.close();
        }
        return wards;
    }

    private static Ward toWard(ResultSet rs) throws SQLException {
        Ward w = new Ward();
        w.setWardId(rs.getInt("ward_id"));
        w.setName(rs.getString("name"));
        w.setMaxPatients(rs.getInt("max_patients"));
        return w;
    }
}
