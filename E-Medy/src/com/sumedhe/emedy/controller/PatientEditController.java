package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;

import com.sumedhe.emedy.Global;
import com.sumedhe.emedy.data.DesignationData;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.model.Designation;
import com.sumedhe.emedy.model.Employee;
import com.sumedhe.emedy.model.Patient;
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
	DatePicker dobInput;

	@FXML
	ComboBox<String> genderInput, bloodGroupInput, consultantInput;

	@FXML
	Button saveButton, backButton, testButton;

	Patient patient;

	public PatientEditController() {
		// this.setPatient(patient);

		String url = "/com/sumedhe/emedy/view/PatientEditView.fxml";
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

		// configure();
	}

	@Override
	public void configure() {
		// BloodGroupData.getList().forEach(bg ->
		// genderInput.getItems().add(bg.getName()));
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		this.firstNameInput.setText(patient.getFirstName());
		this.lastNameInput.setText(patient.getLastName());
		this.nicInput.setText(patient.getNic());
		this.dobInput.setValue(patient.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		this.genderInput.setValue(patient.getGender() == 'M' ? "Male" : "Female");
		this.addressInput.setText(patient.getAddress());
		this.phoneInput.setText(patient.getPhone());
		this.mobileInput.setText(patient.getMobile());
		// this.bloodGroupInput.setValue(patient.getBloodGroupId());
		// this.consultantInput.set
	}

	public void save() {
		// patient.setFirstName(this.firstNameInput.getText());
		// patient.setLastName(this.lastNameInput.getText());
		// patient.setNic(this.nicInput.getText());
		// patient.setDob(Date.from(this.dobInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		// patient.setGender(this.genderInput.getValue() == "Male" ? 'M' : 'F');
		// patient.setAddress(this.addressInput.getText());
		// patient.setPhone(this.phoneInput.getText());
		// patient.setMobile(this.mobileInput.getText());
		// patient.setBloodGroupId(this.);
		// patient.setConsultantId(this.consultantInput);

		try {
			PatientData.save(patient);
		} catch (DBException e) {
			Global.log(e.getMessage());
		}
	}

	public void test() {
		try {

//			Ward w = new Ward("Dialysis", 100);
//			WardData.save(w);

//			Branch b = new Branch("Physical");
//			BranchData.save(b);
			
//			BloodGroup bg = new BloodGroup("A+");
//			BloodGroupData.save(bg);
			
//			Test t = new Test("Blood", 250.25);
//			TestData.save(t);
			
//			Treatment tm = new Treatment("Paracetamol", 360.33);
//			TreatmentData.save(tm);
			
			Designation d = new Designation("Nurse");
			DesignationData.save(d);

			Employee em = new Employee("fname", "lname", "9311..", Date.valueOf("2015-01-01"), 'M', "No 100, Rohana", "0772055141", "070...", Date.valueOf("2015-01-01"), 1);
			EmployeeData.save(em);

		} catch (DBException e) {
			e.printStackTrace();
		}

	}

}
