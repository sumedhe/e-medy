package com.sumedhe.emedy.model;

import java.sql.Date;

import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.data.DesignationData;

public class Employee {
    int employeeId;
    String firstName;
    String lastName;
    String nic;
    Date dob;
    Gender gender;
    String address;
    String phone;
    String mobile;
    Date startDate;
    int designationId;

    
    public Employee(){
        this.firstName = "";
        this.lastName = "";
        this.nic = "";
        this.dob = Date.valueOf("2000-01-01");;
        this.gender = null;
        this.address = "";
        this.phone = "";
        this.mobile = "";
        this.startDate = Date.valueOf("2000-01-01");;
        this.designationId = 0;
    }
    
    public Employee(String firstName, String lastName, String nic, Date dob, Gender gender, String address, String phone, String mobile, Date startDate, int designationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.startDate = startDate;
        this.designationId = designationId;
    }
    

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

    // Extra Getters
    public String getName(){
    	return this.firstName + " " + this.getLastName();
    }
    
    public Designation getDesignation(){
    	return DesignationData.getById(this.designationId);
    }
    
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
    
}

