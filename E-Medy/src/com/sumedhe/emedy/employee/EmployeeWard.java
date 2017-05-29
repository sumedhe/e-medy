package com.sumedhe.emedy.employee;

import com.sumedhe.emedy.misc.Ward;
import com.sumedhe.emedy.misc.WardData;

public class EmployeeWard {
	int employeeWardId;
	int employeeId;
	int wardId;
	
	public EmployeeWard() {
		
	}
	
	public EmployeeWard(Employee e, Ward w){
		this.employeeId = e.getEmployeeId();
		this.wardId = w.getWardId();
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

	
	// EXTRA
	public Employee getEmployee(){
		return EmployeeData.getById(employeeId);
	}
	
//	public void setEmployee(Employee e){
//		this.employeeId = e.getEmployeeId();
//	}
	
	public Ward getWard(){
		return WardData.getById(wardId);
	}
	
//	public void setWard(Ward w){
//		this.wardId = w.getWardId();
//	}
	
	
	public boolean equals(EmployeeWard ew){
		return this.employeeId == ew.employeeId && this.wardId == ew.getWardId();
	}
	
	@Override
	public String toString(){
		return this.getWard().toString();
	}
	
}
