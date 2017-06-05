package com.sumedhe.emedy;

import java.sql.Date;

import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.data.AdmissionTestData;
import com.sumedhe.emedy.data.BloodGroupData;
import com.sumedhe.emedy.data.BranchData;
import com.sumedhe.emedy.data.DesignationData;
import com.sumedhe.emedy.data.DoctorData;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.data.EmployeeWardData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.data.TestData;
import com.sumedhe.emedy.data.TreatmentData;
import com.sumedhe.emedy.data.WardData;
import com.sumedhe.emedy.model.AdmissionTest;
import com.sumedhe.emedy.model.BloodGroup;
import com.sumedhe.emedy.model.Branch;
import com.sumedhe.emedy.model.Designation;
import com.sumedhe.emedy.model.Doctor;
import com.sumedhe.emedy.model.Employee;
import com.sumedhe.emedy.model.EmployeeWard;
import com.sumedhe.emedy.model.Patient;
import com.sumedhe.emedy.model.Test;
import com.sumedhe.emedy.model.Treatment;
import com.sumedhe.emedy.model.Ward;
import com.sumedhe.emedy.service.DBException;

public class testBootstrap {
	public static void test() throws DBException{
		 
		Ward w = new Ward("Dialysis", 100);
		WardData.save(w);

		Branch b = new Branch("Physical");
		BranchData.save(b);

		BloodGroup bg = new BloodGroup("O+");
		BloodGroupData.save(bg);

		Test t = new Test("Blood", 250.25);
		TestData.save(t);

		Treatment tm = new Treatment("Paracetamol", 360.33);
		TreatmentData.save(tm);

		Designation d = new Designation("Nurse", 3500.00);
		DesignationData.save(d);

		Employee em = new Employee("fname", "lname", "9311..", Date.valueOf("2015-01-01"), Gender.Male, "No 100, Rohana",
				"0772055141", "070...", Date.valueOf("2015-01-01"), 1);
		EmployeeData.save(em);

		Doctor doc = new Doctor("fname", "lname", "9311..", Date.valueOf("2015-01-01"), Gender.Male, "No 100, Rohana",
				"0772055141", "070...", Date.valueOf("2015-01-01"), 1, 1);
		DoctorData.save(doc);

		Patient p = new Patient("Abc", "Def", "9411..", Date.valueOf("2015-01-01"), Gender.Male, "Colombo", "078..",
				"07500", 1, 1, Date.valueOf("2016-02-05"));
		PatientData.save(p);

//		Admission ad = new Admission(1, 1, "Dr.Sume", 1, "Moth", "112233445V", "0775522", Date.valueOf("2017-01-01"),
//				false);
//		AdmissionData.save(ad);

		AdmissionTest atest = new AdmissionTest(1, 1, "Fine");
		AdmissionTestData.save(atest);

		Treatment tr = new Treatment("Treatment 01", 150.00);
		TreatmentData.save(tr);

//		AdmissionTreatment at = new AdmissionTreatment(1, 1);
//		AdmissionTreatmentData.save(at);

		EmployeeWard ew = new EmployeeWard(1, 1);
		EmployeeWardData.save(ew);
	}
}
