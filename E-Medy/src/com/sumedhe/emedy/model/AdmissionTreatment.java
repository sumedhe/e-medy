package com.sumedhe.emedy.model;

public class AdmissionTreatment {
	int admissionTreatmentId;
	int admissionId;
	int treatmentId;
	
	public AdmissionTreatment() {
		// TODO Auto-generated constructor stub
	}
	
	public AdmissionTreatment(int admissionId, int treatmentId){
		this.admissionId = admissionId;
		this.treatmentId = treatmentId;
	}
	
	public int getAdmissionTreatmentId() {
		return admissionTreatmentId;
	}
	
	public void setAdmissionTreatmentId(int admissionTreatmentId) {
		this.admissionTreatmentId = admissionTreatmentId;
	}
	
	public int getAdmissionId() {
		return admissionId;
	}
	
	public void setAdmissionId(int admissionId) {
		this.admissionId = admissionId;
	}
	
	public int getTreatmentId() {
		return treatmentId;
	}
	
	public void setTreatmentId(int treatmentId) {
		this.treatmentId = treatmentId;
	}
	

	
}
