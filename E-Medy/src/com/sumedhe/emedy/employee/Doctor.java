package com.sumedhe.emedy.employee;

import java.sql.Date;

public class Doctor extends Employee {
    int doctorId;
    int branchId;

    
    public Doctor(){
        super();
    }
    
    public Doctor(Employee employee){
    	super.setFirstName(employee.getFirstName());
    	super.setLastName(employee.getLastName());
    	super.setNic(employee.getNic());
    	super.setDob(employee.getDob());
    	super.setGender(employee.getGender());
    	super.setAddress(employee.getAddress());
    	super.setPhone(employee.getPhone());
    	super.setMobile(employee.getMobile());
    	super.setStartDate(employee.getStartDate());
    	super.setDesignationId(employee.getDesignationId());    	
    }
    
    public Doctor(String firstName, String lastName, String nic, Date dob, char gender, String address, String phone, String mobile, Date startDate, int designationId, int branchId) {
        super(firstName, lastName, nic, dob, gender, address, phone, mobile, startDate, designationId);
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

	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
    
    
}
