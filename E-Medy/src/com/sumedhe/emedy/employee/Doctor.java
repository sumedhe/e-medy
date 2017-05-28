package com.sumedhe.emedy.employee;

import java.sql.Date;

import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.misc.Branch;
import com.sumedhe.emedy.misc.BranchData;

public class Doctor {
    int doctorId;
    int branchId;
    Employee employee;
    
    public Doctor(){
        employee = new Employee();
    }
    
    public Doctor(Employee employee){
    	this.employee = employee;
    }
    
    public Doctor(Employee employee, int branchId){
    	this.employee = employee;
    	this.branchId = branchId;
    }
    
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
    
    public Employee toEmployee(){
    	return this.employee;
    }
    
    
    public Branch getBranch(){
    	return BranchData.getById(branchId);
    }
    

	@Override
	public String toString() {
		return this.employee.firstName + " " + this.employee.lastName;
	}
    
    
}
