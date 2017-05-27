package com.sumedhe.emedy;

import java.sql.Date;

import com.sumedhe.emedy.admission.Admission;
import com.sumedhe.emedy.admission.AdmissionData;
import com.sumedhe.emedy.admission.AdmissionTest;
import com.sumedhe.emedy.admission.AdmissionTestData;
import com.sumedhe.emedy.admission.AdmissionTreatment;
import com.sumedhe.emedy.admission.AdmissionTreatmentData;
import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.employee.Doctor;
import com.sumedhe.emedy.employee.DoctorData;
import com.sumedhe.emedy.employee.Employee;
import com.sumedhe.emedy.employee.EmployeeData;
import com.sumedhe.emedy.employee.EmployeeWard;
import com.sumedhe.emedy.employee.EmployeeWardData;
import com.sumedhe.emedy.misc.BloodGroup;
import com.sumedhe.emedy.misc.BloodGroupData;
import com.sumedhe.emedy.misc.Branch;
import com.sumedhe.emedy.misc.BranchData;
import com.sumedhe.emedy.misc.Designation;
import com.sumedhe.emedy.misc.DesignationData;
import com.sumedhe.emedy.misc.Test;
import com.sumedhe.emedy.misc.TestData;
import com.sumedhe.emedy.misc.Treatment;
import com.sumedhe.emedy.misc.TreatmentData;
import com.sumedhe.emedy.misc.Ward;
import com.sumedhe.emedy.misc.WardData;
import com.sumedhe.emedy.patient.Patient;
import com.sumedhe.emedy.patient.PatientData;
import com.sumedhe.emedy.service.DBException;

public class test {	
	public static void testbootstrap() throws DBException {

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

		Employee em = new Employee("fname", "lname", "9311..", Date.valueOf("2015-01-01"), 'M', "No 100, Rohana",
				"0772055141", "070...", Date.valueOf("2015-01-01"), 1);
		EmployeeData.save(em);

		Doctor doc = new Doctor("fname", "lname", "9311..", Date.valueOf("2015-01-01"), 'M', "No 100, Rohana",
				"0772055141", "070...", Date.valueOf("2015-01-01"), 1, 1);
		DoctorData.save(doc);

		Patient p = new Patient("Abc", "Def", "9411..", Date.valueOf("2015-01-01"), Gender.Male, "Colombo", "078..",
				"07500", 1, 1, Date.valueOf("2016-02-05"));
		PatientData.save(p);

		Admission ad = new Admission(1, 1, "Dr.Sume", 1, "Moth", "112233445V", "0775522", Date.valueOf("2017-01-01"),
				false);
		AdmissionData.save(ad);

		AdmissionTest atest = new AdmissionTest(1, 1, "Fine");
		AdmissionTestData.save(atest);

		Treatment tr = new Treatment("Treatment 01", 150.00);
		TreatmentData.save(tr);

		AdmissionTreatment at = new AdmissionTreatment(1, 1);
		AdmissionTreatmentData.save(at);

		EmployeeWard ew = new EmployeeWard(1, 1);
		EmployeeWardData.save(ew);

	}
}
