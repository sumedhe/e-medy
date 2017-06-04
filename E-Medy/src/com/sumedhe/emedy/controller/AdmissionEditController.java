package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.EventObject;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.ComboBoxFilterListener;
import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.AdmissionData;
import com.sumedhe.emedy.data.DoctorData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.data.WardData;
import com.sumedhe.emedy.model.Admission;
import com.sumedhe.emedy.model.Doctor;
import com.sumedhe.emedy.model.Patient;
import com.sumedhe.emedy.model.Ward;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Tool;
import com.sumedhe.emedy.tool.Validator;
import com.sumedhe.emedy.tool.ValidatorEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AdmissionEditController extends AnchorPane implements Controllable {

	@FXML
	TextField custodianNameInput, custodianNicInput, custodianPhoneInput;

	@FXML
	TextArea recommendedByInput;

	@FXML
	DatePicker admittedOnInput, dischargedOnInput;

	@FXML
	ComboBox<Patient> patientInput;

	@FXML
	ComboBox<Doctor> confirmedByInput;

	@FXML
	ComboBox<Ward> wardInput;

	@FXML
	ComboBox<String> admissionStateInput;
	
	@FXML
	Button backButton, saveButton, saveAndNewButton;

	Node prev;
	Admission admission;
	Validator validator = new Validator();

	// Constructor
	public AdmissionEditController(Admission admission, Node prev) {
		this.prev = prev;
		this.admission = admission;

		String url = "/com/sumedhe/emedy/view/AdmissionEditView.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	// Initialization
	@Override
	public void initialize() {
		patientInput.getItems().addAll(PatientData.getList());
		confirmedByInput.getItems().addAll(DoctorData.getList());
		wardInput.getItems().addAll(WardData.getList());
		admissionStateInput.getItems().addAll("Discharged", "Not Discharged");
		new ComboBoxFilterListener<Patient>(patientInput);
		new ComboBoxFilterListener<Doctor>(confirmedByInput);
		new ComboBoxFilterListener<Ward>(wardInput);
		new ComboBoxFilterListener<String>(admissionStateInput);
		
		setHandlers();
		setAdmission(admission);
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		PatientData.getCache().addCacheEventListener(new CacheEventListener() {			
			@Override
			public void updated(EventObject e) {
				patientInput.getItems().clear();
				patientInput.getItems().addAll(PatientData.getList());
			}
		});
		DoctorData.getCache().addCacheEventListener(new CacheEventListener() {			
			@Override
			public void updated(EventObject e) {
				confirmedByInput.getItems().clear();
				confirmedByInput.getItems().addAll(DoctorData.getList());
			}
		});
		WardData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				wardInput.getItems().clear();
				wardInput.getItems().addAll(WardData.getList());
			}
		});
		backButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(this.prev);
		});
		saveButton.setOnAction(e -> {
			if (validator.checkValidity(new ValidatorEvent(this))) {
				save();
			}
		});
		saveAndNewButton.setOnAction(e -> {
			if (validator.checkValidity(new ValidatorEvent(this))) {
				save();
				setAdmission(new Admission());
			}
		});

		// Set handlers for Validation Checking
		validator.addToCheckNull(patientInput);
		validator.addToCheckEmpty(recommendedByInput);
		validator.addToCheckNull(confirmedByInput);
		validator.addToCheckNull(wardInput);
		validator.addToCheckNull(admittedOnInput);
		validator.addToCheckNull(admissionStateInput);
		validator.addToCheckEmpty(custodianNameInput);
		validator.addToCheckEmpty(custodianNameInput);
		validator.addToCheckNic(custodianNicInput);
		validator.addToCheckPhone(custodianPhoneInput);
		

	}

	// Fill the form from the given object
	public void setAdmission(Admission admission) {
		this.admission = admission;
		this.patientInput.setValue(admission.getPatient());
		this.recommendedByInput.setText(admission.getRecommendedBy());
		this.confirmedByInput.setValue(admission.getConfirmedDoctor());
		this.wardInput.setValue(admission.getWard());
		this.admittedOnInput.setValue(Tool.Date2DP(admission.getAdmittedOn()));
		this.admissionStateInput.setValue(admission.getIsDischarged() ? "Discharged" : "Not Discharged");
		this.dischargedOnInput.setValue(Tool.Date2DP(admission.getDischargedOn()));
		this.custodianNameInput.setText(admission.getCustodianName());
		this.custodianNicInput.setText(admission.getCustodianNIC());
		this.custodianPhoneInput.setText(admission.getCustodianPhone());
	}

	// Create a object from the form and save it
	public void save() {
		try {
			admission.setPatientId(this.patientInput.getValue().getPatientId());
			admission.setRecommendedBy(this.recommendedByInput.getText());
			admission.setConfirmedDoctorId(this.confirmedByInput.getValue().getDoctorId());
			admission.setWardId(this.wardInput.getValue().getWardId());
			admission.setAdmittedOn(Tool.DP2Date(admittedOnInput));
			admission.setDischarged(this.admissionStateInput.getValue().toString().equals("Discharged"));
			admission.setDischargedOn(Tool.DP2Date(dischargedOnInput));
			admission.setCustodianName(this.custodianNameInput.getText());
			admission.setCustodianNIC(this.custodianNicInput.getText());
			admission.setCustodianPhone(this.custodianPhoneInput.getText());
			AdmissionData.save(admission);
			Global.showNotification("Saved...", NotificationType.Success);
		} catch (DBException e) {
			Global.log(e.getMessage());
		} catch (Exception e) {
			Global.log(e.getMessage());
		}
	}

}
