package com.sumedhe.emedy.employee;

public class EmployeeWard {
	int employeeWardId;
	int employeeId;
	int wardId;
	
	public EmployeeWard() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeWard(int employeeId, int wardId){
		this.employeeId = employeeId;
		this.wardId = wardId;
	}
	
	public int getEmployeeWardId() {
		return employeeWardId;
	}
	
	public void setEmployeeWardId(int employeeWardId) {
		this.employeeWardId = employeeWardId;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public int getWardId() {
		return wardId;
	}
	
	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	
}
