package com.sumedhe.emedy.model;

import java.sql.Date;

import com.sumedhe.emedy.data.DoctorData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.data.WardData;

public class Admission {
	int admissionId;
	int patientId;
	int wardId;
	String recommendedBy;
	int confirmedDoctorId;
	String custodianName;
	String custodianNIC;
	String custodianPhone;
	Date admittedOn;
	boolean isDischarged;
	Date dischargedOn;

	public Admission() {
		this.admissionId = 0;
		this.patientId = 0;
		this.wardId = 0;
		this.recommendedBy = "";
		this.confirmedDoctorId = 0;
		this.custodianName = "";
		this.custodianNIC = "";
		this.custodianPhone = "";
		this.admittedOn = null;
		this.isDischarged = false;
		this.dischargedOn = null;
	}

	public int getAdmissionId() {
		return admissionId;
	}

	public void setAdmissionId(int admissionId) {
		this.admissionId = admissionId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getWardId() {
		return wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
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

	public Date getAdmittedOn() {
		return admittedOn;
	}

	public void setAdmittedOn(Date admittedOn) {
		this.admittedOn = admittedOn;
	}

	public boolean getIsDischarged() {
		return this.isDischarged;
	}

	public void setDischarged(boolean isDischarged) {
		this.isDischarged = isDischarged;
	}

	public Date getDischargedOn() {
		return dischargedOn;
	}

	public void setDischargedOn(Date dischargedOn){
		this.dischargedOn = dischargedOn;
	}

	// Extra //
	public Patient getPatient() {
		return PatientData.getById(patientId);
	}

	public Ward getWard() {
		return WardData.getById(wardId);
	}

	public Doctor getConfirmedDoctor() {
		return DoctorData.getById(confirmedDoctorId);
	}
	
	public String getDischarged(){
		return this.isDischarged ? "Yes" : "No";
	}
}
