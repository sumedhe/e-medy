package com.sumedhe.emedy.admission;

import java.sql.Date;

public class Admission {
    int admissionId;    
    int wardId;
    int patientId;
    String recommendedBy;
    int confirmedDoctorId;
    String custodianName;
    String custodianNIC;
    String custodianPhone;
    Date registeredOn;
    boolean isDischarged;

    
    public Admission(){
        
    }
    
    public Admission(int wardId, int patientId, String recommendedBy, int confirmedDoctorId, String custodianName, String custodianNic, String custodianPhone, Date registeredOn, boolean isDischarged) {
        this.wardId = wardId;
        this.patientId = patientId;
        this.recommendedBy = recommendedBy;
        this.confirmedDoctorId = confirmedDoctorId;
        this.custodianName = custodianName;
        this.custodianNIC = custodianNic;
        this.custodianPhone = custodianPhone;
        this.registeredOn = registeredOn;
        this.isDischarged = isDischarged;
    } 
    

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public int getConfirmedDoctorId() {
        return confirmedDoctorId;
    }

    public void setConfirmedDoctorId(int confirmedDoctorId) {
        this.confirmedDoctorId = confirmedDoctorId;
    }

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }

    public String getCustodianNIC() {
        return custodianNIC;
    }

    public void setCustodianNIC(String custodianNIC) {
        this.custodianNIC = custodianNIC;
    }

    public String getCustodianPhone() {
        return custodianPhone;
    }

    public void setCustodianPhone(String custodianPhone) {
        this.custodianPhone = custodianPhone;
    }

    public Date getRegisteredOn() {
		return registeredOn;
	}
    
    public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}
    
    public boolean getIsDischarged(){
    	return this.isDischarged;
    }
    
    public void setDischarged(boolean isDischarged) {
		this.isDischarged = isDischarged;
	}

    
    
}

