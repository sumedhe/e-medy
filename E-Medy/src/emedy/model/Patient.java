package emedy.model;

import java.util.Date;

public class Patient {
    int patient_id;
    String first_name;
    String last_name;
    String nic;
    Date dob;
    char gender;
    String address;
    String phone;
    String mobile;
    int consultant_id;
    int blood_group;
    Date registered_on;
    
    

    public Patient(int patient_id, String first_name, String last_name, String nic, Date dob, char gender, String address, String phone, String mobile, int consultant_id, int blood_group, Date registered_on) {
        this.patient_id = patient_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.nic = nic;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.mobile = mobile;
        this.consultant_id = consultant_id;
        this.blood_group = blood_group;
        this.registered_on = registered_on;
    }

    
    
    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public int getConsultant_id() {
        return consultant_id;
    }

    public void setConsultant_id(int consultant_id) {
        this.consultant_id = consultant_id;
    }

    public int getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(int blood_group) {
        this.blood_group = blood_group;
    }

    public Date getRegistered_on() {
        return registered_on;
    }

    public void setRegistered_on(Date registered_on) {
        this.registered_on = registered_on;
    }
   
}
