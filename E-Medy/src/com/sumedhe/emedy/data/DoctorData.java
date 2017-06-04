package com.sumedhe.emedy.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Doctor;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class DoctorData {

	static Cache<Doctor> cache = new Cache<>();

	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM doctor");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Doctor d = toDoctor(rs);
				cache.put(d.getDoctorId(), d);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}

	public static void save(Doctor doctor) throws DBException {
		boolean isNew = doctor.getDoctorId() == 0;
		try {
			EmployeeData.save(doctor.getEmployee());

			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO doctor(branch_id, employee_id) values(?, ?)");
			} else {
				sqry = DB.newQuery("UPDATE doctor SET branch_id = ?, employee_id = ? WHERE doctor_id = ?");
			}

			sqry.setInt(1, doctor.getBranchId());
			sqry.setInt(2, doctor.getEmployeeId());
			if (!isNew) {
				sqry.setInt(3, doctor.getDoctorId());
			}

			sqry.executeUpdate();
			if (isNew) {
				doctor.setDoctorId(DB.execGetInt("SELECT MAX(doctor_id) from doctor"));
			}

			cache.put(doctor.getDoctorId(), doctor);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static void delete(int doctorId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM doctor WHERE doctor_id = ?");
			sqry.setInt(1, doctorId);
			sqry.executeUpdate();
			EmployeeData.delete(DoctorData.getById(doctorId).getEmployee().getEmployeeId());
			cache.remove(doctorId);
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Doctor getById(int id) {
		if (id == 0) {
			return null;
		}
		Doctor d = cache.get(id);
		if (d == null) {
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM doctor WHERE doctor_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				d = toDoctor(rs);
				cache.put(d.getDoctorId(), d);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage() + Integer.toString(id));
			} finally {
				DB.close();
			}
		}
		return d;
	}

	public static List<Doctor> getList() {
		return cache.getItemList();
	}

	public static Doctor getByEmployeeId(int employeeId) {
		return cache.getStream().filter(x -> x.getEmployee().getEmployeeId() == employeeId).findFirst().get();
	}

	private static Doctor toDoctor(ResultSet rs) throws SQLException {
		Doctor d = new Doctor();
		d.setDoctorId(rs.getInt("doctor_id"));
		d.setBranchId(rs.getInt("branch_id"));
		d.setEmployeeId(rs.getInt("employee_id"));
		return d;
	}

	public static Cache<Doctor> getCache() {
		return cache;
	}
}
