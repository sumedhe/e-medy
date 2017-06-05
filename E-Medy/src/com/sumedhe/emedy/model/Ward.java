package com.sumedhe.emedy.model;

import com.sumedhe.emedy.data.AdmissionData;
import com.sumedhe.emedy.data.EmployeeWardData;

public class Ward {
	int wardId;
	String name;
	int maxPatients;

	public Ward() {

	}

	public Ward(String name, int maxPatients) {
		this.name = name;
		this.maxPatients = maxPatients;
	}

	public int getWardId() {
		return wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxPatients() {
		return maxPatients;
	}

	public void setMaxPatients(int maxPatients) {
		this.maxPatients = maxPatients;
	}

	// EXTRA //
	public int getPatientCount() {
		return (int) AdmissionData.getCache().getStream()
				.filter(x -> x.getIsDischarged() == false && x.getWardId() == this.wardId).count();
	}

	public int getEmployeeCount() {
		return (int) EmployeeWardData.getCache().getStream().filter(x -> x.getWardId() == this.wardId).count();
	}

	@Override
	public String toString() {
		return this.name;
	}

}
