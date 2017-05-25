package com.sumedhe.emedy.misc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;

public class TreatmentData {

	public static void save(Treatment treatment) throws DBException {
		boolean isNew = treatment.getTreatmentId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO treatment(name, fee) values(?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE treatment SET name = ?, fee = ? WHERE treatment_id = ?");
			}

			sqry.setString(1, treatment.getName());
			sqry.setDouble(2, treatment.getFee());
			if (!isNew) {
				sqry.setInt(3, treatment.getTreatmentId());
			}

			sqry.executeUpdate();
			if (isNew) {
				treatment.setTreatmentId(DB.execGetInt("SELECT MAX(treatment_id) from treatment"));
			}

		} catch (DBException | SQLException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}


	public static int delete(int treatmentId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM treatment WHERE treatment_id = ?");
			sqry.setInt(1, treatmentId);
			return sqry.executeUpdate();
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Treatment findById(int id) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM treatment WHERE treatment_id = ?");
			sqry.setInt(1, id);
			ResultSet rs = sqry.executeQuery();
			rs.next();
			return toTreatment(rs);
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static List<Treatment> getList() throws DBException {
		List<Treatment> treatments = new ArrayList<>();
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM treatment");
			ResultSet rs = sqry.executeQuery();
			while (rs.next()) {
				treatments.add(toTreatment(rs));
			}
		} catch (SQLException | DBException ex) {
			throw new DBException("Error: " + ex.getMessage());
		} finally {
			DB.close();
		}
		return treatments;
	}

	private static Treatment toTreatment(ResultSet rs) throws SQLException {
		Treatment w = new Treatment();
		w.setTreatmentId(rs.getInt("treatment_id"));
		w.setName(rs.getString("name"));
		w.setFee(rs.getDouble("fee"));
		return w;
	}
}
