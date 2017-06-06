package com.sumedhe.emedy.model;

import java.sql.Date;

import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.data.BranchData;
import com.sumedhe.emedy.data.EmployeeData;

public class Doctor {
    int doctorId;
    int branchId;
    int employeeId;
    Employee employee;
    
    public Doctor(){
//        this.employee = new Employee();
    }
    
    public Doctor(Employee employee){
    	this.employee = employee;
    }
    
//    public Doctor(Employee employee, int branchId){
//    	this.employee = employee;
//    	this.branchId = branchId;
//    }
    
    public Doctor(String firstName, String lastName, String nic, Date dob, Gender gender, String address, String phone, String mobile, Date startDate, int designationId, int branchId) {
    	this.employee = new Employee(firstName, lastName, nic, dob, gender, address, phone, mobile, startDate, designationId);
        this.branchId = branchId;
    }
    

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    
    public int getEmployeeId() {
		return employeeId;
	}
    
    public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
    
    public Employee getEmployee(){
    	if (this.employee == null){
    		this.employee = EmployeeData.getById(employeeId);
    	}
    	return this.employee;
    }
    
    
    public Branch getBranch(){
    	return BranchData.getById(branchId);
    }
    

	@Override
	public String toString() {
		return this.getEmployee().firstName + " " + this.getEmployee().lastName;
	}
    
    
}
