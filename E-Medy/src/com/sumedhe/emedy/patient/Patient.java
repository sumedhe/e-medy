package com.sumedhe.emedy.patient;

import java.sql.Date;

import com.sumedhe.emedy.common.Gender;

public class Patient {
    int patientId;
    String firstName;
    String lastName;
    String nic;
    Date dob;
    Gender gender;
    String address;
    String phone;
    String mobile;
    int bloodGroupId;
    int consultantId;
    Date registeredOn;
    

    public Patient(){
        this.firstName = "";
        this.lastName = "";
        this.nic = "";
        this.dob = Date.valueOf("2000-01-01");
        this.gender = Gender.Male;
        this.address = "";
        this.phone = "";
        this.mobile = "";
        this.bloodGroupId = 0;
        this.consultantId = 0;
        this.registeredOn = Date.valueOf(java.time.LocalDate.now());
    }
    
    public Patient(String firstName, String lastName, String nic, Date dob, Gender gender, String address, String phone, String mobile, int bloodGroup, int consultantId, Date registeredOn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.bloodGroupId = bloodGroup;
        this.consultantId = consultantId;
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
    
    public int getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(int bloodGroup) {
        this.bloodGroupId = bloodGroup;
    }

    public int getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(int consultantId) {
        this.consultantId = consultantId;
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
   
    

   
}

