package com.sumedhe.emedy.model;

import com.sumedhe.emedy.data.TestData;

public class AdmissionTest {
	int admissionTestId;
	int admissionId;
	int testId;
	String result;
	
	public AdmissionTest() {
		this.result = "";
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

	// EXTRA //
	public Test getTest(){
		return TestData.getById(testId);
	}
	
	public String getTestName(){
		return testId != 0 ? getTest().getName() : "(New Test)";
	}
	
	public Double getFee(){
		return testId != 0 ? getTest().getFee() : 0.00;
	}
	
}
