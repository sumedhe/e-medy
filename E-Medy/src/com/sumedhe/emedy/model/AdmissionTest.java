package com.sumedhe.emedy.model;

public class AdmissionTest {
	int admissionTestId;
	int admissionId;
	int testId;
	String result;
	
	public AdmissionTest() {
		// TODO Auto-generated constructor stub
	}
	
	public AdmissionTest(int admissionId, int testId, String result){
		this.admissionId = admissionId;
		this.testId = testId;
		this.result = result;
	}
	
	public int getAdmissionTestId() {
		return admissionTestId;
	}
	
	public void setAdmissionTestId(int admissionTestId) {
		this.admissionTestId = admissionTestId;
	}
	
	public int getAdmissionId() {
		return admissionId;
	}
	
	public void setAdmissionId(int admissionId) {
		this.admissionId = admissionId;
	}
	
	public int getTestId() {
		return testId;
	}
	
	public void setTestId(int testId) {
		this.testId = testId;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

	
}
