package com.sumedhe.emedy.test;

import com.sumedhe.emedy.config.Prefs;
import com.sumedhe.emedy.model.Doctor;

public class Main{
	public static void main(String[] args){
		Prefs.load();

		
		System.out.println("This is a test");
		
//		PatientData.updateCache();
//		WardData.updateCache();
//		
//		AdmissionData.updateCache();
//		AdmissionData.getCache().getItemList().forEach(x -> Global.log(x.toString()));
//		EmployeeData.updateCache();
//		EmployeeData.getList().forEach(x -> Global.log(x.toString()));
//		Global.log(Integer.toString(AdmissionData.getCache().getItemList().size()));
////		List<Admission> list = AdmissionData.getList();
////		list.forEach(x -> Global.log(x.toString()));
//		
//
//		DoctorData.updateCache();
//		DoctorData.getList().forEach(x -> Global.log(x.getEmployee().toString()));


//		AttendanceData.updateCache();
//		Attendance a = new Attendance();
//		a.setDate(Date.valueOf("2010-02-10"));
//		a.setEmployeeId(1);
//		try {
//			AttendanceData.save(a);
//		} catch (DBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		Doctor d = new Doctor();
	}
}
