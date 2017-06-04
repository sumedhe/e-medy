package com.sumedhe.emedy.test;

import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.config.Prefs;
import com.sumedhe.emedy.data.AdmissionData;
import com.sumedhe.emedy.data.DoctorData;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.data.WardData;

public class Main{
	public static void main(String[] args){
		Prefs.load();

		
		System.out.println("This is a test");
		
		PatientData.updateCache();
		WardData.updateCache();
		
		AdmissionData.updateCache();
		AdmissionData.getCache().getItemList().forEach(x -> Global.log(x.toString()));
		EmployeeData.updateCache();
		EmployeeData.getList().forEach(x -> Global.log(x.toString()));
		Global.log(Integer.toString(AdmissionData.getCache().getItemList().size()));
//		List<Admission> list = AdmissionData.getList();
//		list.forEach(x -> Global.log(x.toString()));
		

		DoctorData.updateCache();
		DoctorData.getList().forEach(x -> Global.log(x.getEmployee().toString()));

		
	}
}
