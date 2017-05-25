package com.sumedhe.emedy.patient;

import java.io.IOException;
import java.sql.Date;

import com.sumedhe.emedy.admission.Admission;
import com.sumedhe.emedy.admission.AdmissionData;
import com.sumedhe.emedy.admission.AdmissionTest;
import com.sumedhe.emedy.admission.AdmissionTestData;
import com.sumedhe.emedy.admission.AdmissionTreatment;
import com.sumedhe.emedy.admission.AdmissionTreatmentData;
import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.common.IController;
import com.sumedhe.emedy.common.Tool;
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
import com.sumedhe.emedy.service.DBException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PatientEditController extends AnchorPane implements IController {

	@FXML
	TextField firstNameInput, lastNameInput, nicInput, phoneInput, mobileInput;

	@FXML
	TextArea addressInput;

	@FXML
	DatePicker dobInput, registeredOnInput;

	@FXML
	ComboBox<Gender> genderInput;
	
	@FXML
	ComboBox<BloodGroup> bloodGroupInput;
	
	@FXML
	ComboBox<Doctor> consultantInput;
	
	

	@FXML
	Button saveButton, backButton, testButton;

	Patient patient;

	public PatientEditController(Patient patient) {
		this.patient = patient;
		
		String url = "/com/sumedhe/emedy/patient/PatientEditView.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void initialize() {
		saveButton.setOnAction(e -> {
			save();
		});
		testButton.setOnAction(e -> {
			test();
		});

		setHandlers();
		setPatient(patient);
	}

	@Override
	public void setHandlers() {
		genderInput.getItems().addAll(Gender.Male, Gender.Female);
		
		try {
			bloodGroupInput.getItems().addAll(BloodGroupData.getList());
			consultantInput.getItems().addAll(DoctorData.getList());
		} catch (DBException e) {
			Global.log(e.getMessage());
		}

	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		this.firstNameInput.setText(patient.getFirstName());
		this.lastNameInput.setText(patient.getLastName());
		this.nicInput.setText(patient.getNic());
		this.dobInput.setValue(patient.getDob().toLocalDate());
		this.genderInput.setValue(patient.getGender() == 'M' ? Gender.Male : Gender.Female);
		this.addressInput.setText(patient.getAddress());
		this.phoneInput.setText(patient.getPhone());
		this.mobileInput.setText(patient.getMobile());
		this.bloodGroupInput.setValue(Tool.getBloodGroupFrom(bloodGroupInput.getItems(), patient.getBloodGroupId()));
		this.consultantInput.setValue(Tool.getDoctorFrom(consultantInput.getItems(), patient.getConsultantId()));
		this.registeredOnInput.setValue(patient.getRegisteredOn().toLocalDate());
	}

	public void save() {
		 patient.setFirstName(this.firstNameInput.getText());
		 patient.setLastName(this.lastNameInput.getText());
		 patient.setNic(this.nicInput.getText());
		 patient.setDob(Date.valueOf(this.dobInput.getValue().toString()));
		 patient.setGender(this.genderInput.getValue() == Gender.Male ? 'M' : 'F');
		 patient.setAddress(this.addressInput.getText());
		 patient.setPhone(this.phoneInput.getText());
		 patient.setMobile(this.mobileInput.getText());
		 patient.setBloodGroupId(this.bloodGroupInput.getValue().getBloodGroupId());
		 patient.setConsultantId(this.consultantInput.getValue().getDoctorId());

		try {
			PatientData.save(patient);
		} catch (DBException e) {
			Global.log(e.getMessage());
		}
	}

	public void test() {
//		try {
//
//			testbootstrap();
//			
//
//		} catch (DBException e) {
//			e.printStackTrace();
//		}
		
		System.out.println((java.time.LocalDate.now()));
		
	}

	public void testbootstrap() throws DBException {

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

		Patient p = new Patient("Abc", "Def", "9411..", Date.valueOf("2015-01-01"), 'M', "Colombo", "078..", "07500", 1,
				1, Date.valueOf("2016-02-05"));
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
