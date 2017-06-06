package com.sumedhe.emedy.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.model.Attendance;
import com.sumedhe.emedy.service.DB;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Cache;

public class AttendanceData {

	static Cache<Attendance> cache = new Cache<>();

	// Updating the cache //
	public static void updateCache() {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("SELECT * FROM attendance");
			ResultSet rs = sqry.executeQuery();
			cache.editingMode(true);
			cache.clear();
			while (rs.next()) {
				Attendance b = toAttendance(rs);
				cache.put(b.getAttendanceId(), b);
			}
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			cache.editingMode(false);
			DB.close();
		}
	}

	// Saving data //
	public static void save(Attendance attendance) throws DBException {
		boolean isNew = attendance.getAttendanceId() == 0;
		try {
			DB.open();
			PreparedStatement sqry;

			if (isNew) {
				sqry = DB.newQuery("INSERT INTO attendance(date, employee_id) values(?,?)");
			} else {
				sqry = DB.newQuery("UPDATE attendance SET date = ?, employee_id = ? WHERE attendance_id = ?");
			}

			sqry.setDate(1, attendance.getDate());
			sqry.setInt(2, attendance.getEmployeeId());
			if (!isNew) {
				sqry.setInt(3, attendance.getAttendanceId());
			}

			sqry.executeUpdate();
			if (isNew) {
				attendance.setAttendanceId(DB.execGetInt("SELECT MAX(attendance_id) from attendance"));
			}

			cache.put(attendance.getAttendanceId(), attendance);

		} catch (DBException | SQLException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	// Deleting an item //
	public static void delete(int attendanceId) throws DBException {
		try {
			DB.open();
			PreparedStatement sqry = DB.newQuery("DELETE FROM attendance WHERE attendance_id = ?");
			sqry.setInt(1, attendanceId);
			sqry.executeUpdate();
			cache.remove(attendanceId); // Remove from cache
		} catch (SQLException | DBException ex) {
			Global.logError(ex.getMessage());
		} finally {
			DB.close();
		}
	}

	public static Attendance getById(int id) {
		if (id == 0) {
			return null;
		}
		Attendance b = cache.get(id);
		if (b == null) {
			try {
				DB.open();
				PreparedStatement sqry = DB.newQuery("SELECT * FROM attendance WHERE attendance_id = ?");
				sqry.setInt(1, id);
				ResultSet rs = sqry.executeQuery();
				rs.next();
				b = toAttendance(rs);
				cache.put(b.getAttendanceId(), b);
			} catch (SQLException | DBException ex) {
				Global.logError(ex.getMessage());
			} finally {
				DB.close();
			}
		}
		return b;
	}

	// Get the list of items //
	public static List<Attendance> getList() {
		return cache.getItemList();
	}
	
	// Get the list of items //
	public static List<Attendance> getByDate(Date date){
		return cache.getStream().filter(x -> x.getDate().toString().equals(date.toString())).collect(Collectors.toList());
	}

	private static Attendance toAttendance(ResultSet rs) throws SQLException {
		Attendance b = new Attendance();
		b.setAttendanceId(rs.getInt("attendance_id"));
		b.setDate(rs.getDate("date"));
		b.setEmployeeId(rs.getInt("employee_id"));
		return b;
	}

	public static Cache<Attendance> getCache() {
		return cache;
	}

}
