package com.sumedhe.emedy.model;

import java.sql.Date;

import com.sumedhe.emedy.data.EmployeeData;

public class Attendance {
	int attendanceId;
	Date date;
	int employeeId;
	
	public Attendance() {
		// TODO Auto-generated constructor stub
	}
	
	public int getAttendanceId() {
		return attendanceId;
	}
	
	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	// EXTRA //
	public Employee getEmployee(){
		return EmployeeData.getById(employeeId);
	}
	
	public String getEmployeeName(){
		return employeeId != 0 ? getEmployee().getName() : "";
	}
	
}
