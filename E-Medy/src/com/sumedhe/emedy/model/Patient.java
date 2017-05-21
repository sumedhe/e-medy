package com.sumedhe.emedy.model;

import java.text.ParseException;
import java.util.Date;

import com.sumedhe.emedy.Global;
import com.sumedhe.emedy.data.BloodGroupData;
import com.sumedhe.emedy.service.DBException;

public class Patient {
    int patientId;
    String firstName;
    String lastName;
    String nic;
    Date dob;
    char gender;
    String address;
    String phone;
    String mobile;
    int consultantId;
    int bloodGroupId;
    Date registeredOn;
    

    public Patient(){
        this.firstName = "";
        this.lastName = "";
        this.nic = "";
        try {
			this.dob = Global.fmt.parse("2015-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        this.gender = 'M';
        this.address = "";
        this.phone = "";
        this.mobile = "";
        this.consultantId = 0;
        this.bloodGroupId = 0;
        try {
			this.registeredOn = Global.fmt.parse("2015-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Patient(String firstName, String lastName, String nic, Date dob, char gender, String address, String phone, String mobile, int consultantId, int bloodGroup, Date registeredOn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.consultantId = consultantId;
        this.bloodGroupId = bloodGroup;
        this.registeredOn = registeredOn;
    }
    
    

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
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

    public int getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(int consultantId) {
        this.consultantId = consultantId;
    }

    public int getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(int bloodGroup) {
        this.bloodGroupId = bloodGroup;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }
    
    
    // Extra Getters
    public String getName(){
    	return this.firstName + " " + this.getLastName();
    }
    
    public String getBloodGroupName() throws DBException{
    	return BloodGroupData.findById(this.bloodGroupId).getName();
    }
    

   
}

