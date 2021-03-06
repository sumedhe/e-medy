package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.EventObject;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.ComboBoxFilterListener;
import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.BloodGroupData;
import com.sumedhe.emedy.data.DoctorData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.model.BloodGroup;
import com.sumedhe.emedy.model.Doctor;
import com.sumedhe.emedy.model.Patient;
import com.sumedhe.emedy.service.DBException;
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

public class PatientEditController extends AnchorPane implements Controllable {

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
	Button backButton, saveButton, saveAndNewButton;

	Node prev;
	Patient patient;
	Validator validator = new Validator();

	// Constructor
	public PatientEditController(Patient patient, Node prev) {
		this.prev = prev;
		this.patient = patient;

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

	// Initialization
	@Override
	public void initialize() {
		genderInput.getItems().addAll(Gender.Male, Gender.Female);
		bloodGroupInput.getItems().addAll(BloodGroupData.getList());
		consultantInput.getItems().addAll(DoctorData.getList());
		new ComboBoxFilterListener<Gender>(genderInput);
		new ComboBoxFilterListener<BloodGroup>(bloodGroupInput);
		new ComboBoxFilterListener<Doctor>(consultantInput);
		
		setHandlers();
		setPatient(patient);
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		DoctorData.getCache().addCacheEventListener(new CacheEventListener() {			
			@Override
			public void updated(EventObject e) {
				consultantInput.getItems().clear();
				consultantInput.getItems().addAll(DoctorData.getList());
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
				setPatient(new Patient());
			}
		});

		// Set handlers for Validation Checking
		validator.addToCheckEmpty(firstNameInput);
		validator.addToCheckEmpty(lastNameInput);
		validator.addToCheckNic(nicInput);
		validator.addToCheckNull(genderInput);
		validator.addToCheckPhone(phoneInput);
		validator.addToCheckPhone(mobileInput);
		validator.addToCheckNull(bloodGroupInput);
		validator.addToCheckNull(consultantInput);

	}

	// Fill the form from the given object
	public void setPatient(Patient patient) {
		this.patient = patient;
		this.firstNameInput.setText(patient.getFirstName());
		this.lastNameInput.setText(patient.getLastName());
		this.nicInput.setText(patient.getNic());
		this.dobInput.setValue(patient.getDob().toLocalDate());
		this.genderInput.setValue(patient.getGender());
		this.addressInput.setText(patient.getAddress());
		this.phoneInput.setText(patient.getPhone());
		this.mobileInput.setText(patient.getMobile());
		this.bloodGroupInput.setValue(patient.getBloodGroup());
		this.consultantInput.setValue(patient.getConsultant());
		this.registeredOnInput.setValue(patient.getRegisteredOn().toLocalDate());
	}

	// Create a object from the form and save it
	public void save() {
		try {
			patient.setFirstName(this.firstNameInput.getText());
			patient.setLastName(this.lastNameInput.getText());
			patient.setNic(this.nicInput.getText());
			patient.setDob(Date.valueOf(this.dobInput.getValue().toString()));
			patient.setGender(this.genderInput.getValue());
			patient.setAddress(this.addressInput.getText());
			patient.setPhone(this.phoneInput.getText());
			patient.setMobile(this.mobileInput.getText());
			patient.setBloodGroupId(this.bloodGroupInput.getValue().getBloodGroupId());
			patient.setConsultantId(this.consultantInput.getValue().getDoctorId());
			patient.setRegisteredOn(Date.valueOf(this.registeredOnInput.getValue()));

			PatientData.save(patient);
			Global.showNotification("Saved...", NotificationType.Success);
		} catch (DBException e) {
			Global.log(e.getMessage());
		} catch (Exception e) {
			Global.log(e.getMessage());
		}
	}

}
