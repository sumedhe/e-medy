package com.sumedhe.emedy.model;

import com.sumedhe.emedy.data.TreatmentData;

public class AdmissionTreatment {
	int admissionTreatmentId;
	int admissionId;
	int treatmentId;
	String note;
	
	public AdmissionTreatment() {
		this.note = "";
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
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	// EXTRA //
	public Treatment getTreatment(){
		return TreatmentData.getById(treatmentId);
	}
	
	public String getTreatmentName(){
		return treatmentId != 0 ? getTreatment().getName() : "(New Treatment)";
	}
	
	public Double getFee(){
		return treatmentId != 0 ? getTreatment().getFee() : 0.00;
	}
	
	
}
