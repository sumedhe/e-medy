package com.sumedhe.emedy.employee;

import java.io.IOException;
import java.sql.Date;

import com.sumedhe.emedy.common.ComboBoxFilterListener;
import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.common.IController;
import com.sumedhe.emedy.common.Tool;
import com.sumedhe.emedy.common.Validator;
import com.sumedhe.emedy.common.ValidatorEvent;
import com.sumedhe.emedy.misc.Designation;
import com.sumedhe.emedy.misc.DesignationData;
import com.sumedhe.emedy.service.DBException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EmployeeEditController extends AnchorPane implements IController {

	@FXML
	TextField firstNameInput, lastNameInput, nicInput, phoneInput, mobileInput;

	@FXML
	TextArea addressInput;

	@FXML
	DatePicker dobInput, startDateInput;

	@FXML
	ComboBox<Gender> genderInput;

	@FXML
	ComboBox<Designation> designationInput;

	@FXML
	Button backButton, saveButton, saveAndNewButton;

	Node prev;
	Employee employee;
	Validator validator = new Validator();

	// Constructor
	public EmployeeEditController(Employee employee, Node prev) {
		this.prev = prev;
		this.employee = employee;

		String url = "/com/sumedhe/emedy/employee/EmployeeEditView.fxml";
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
		designationInput.getItems().addAll(DesignationData.getList());
		new ComboBoxFilterListener<Gender>(genderInput);
		new ComboBoxFilterListener<Designation>(designationInput);

		setHandlers();
		setEmployee(employee);
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		backButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(this.prev);
		});
		saveButton.setOnAction(e -> {
			if (validator.checkValidity(new ValidatorEvent(this))) {
				save();
				EmployeeController pc = (EmployeeController) this.prev;
				pc.loadData();
				Global.getHome().setWorkPanel(pc);
			}
		});
		saveAndNewButton.setOnAction(e -> {
			if (validator.checkValidity(new ValidatorEvent(this))) {
				save();
				setEmployee(new Employee());
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
	public void setEmployee(Employee employee) {
		this.employee = employee;
		this.firstNameInput.setText(employee.getFirstName());
		this.lastNameInput.setText(employee.getLastName());
		this.nicInput.setText(employee.getNic());
		this.dobInput.setValue(employee.getDob().toLocalDate());
		this.genderInput.setValue(employee.getGender());
		this.addressInput.setText(employee.getAddress());
		this.phoneInput.setText(employee.getPhone());
		this.mobileInput.setText(employee.getMobile());
		this.bloodGroupInput.setValue(Tool.getBloodGroupFrom(bloodGroupInput.getItems(), employee.getBloodGroupId()));
		this.consultantInput.setValue(Tool.getDoctorFrom(consultantInput.getItems(), employee.getConsultantId()));
		this.registeredOnInput.setValue(employee.getRegisteredOn().toLocalDate());
	}

	// Create a object from the form and save it
	public void save() {
		try {
			employee.setFirstName(this.firstNameInput.getText());
			employee.setLastName(this.lastNameInput.getText());
			employee.setNic(this.nicInput.getText());
			employee.setDob(Date.valueOf(this.dobInput.getValue().toString()));
			employee.setGender(this.genderInput.getValue());
			employee.setAddress(this.addressInput.getText());
			employee.setPhone(this.phoneInput.getText());
			employee.setMobile(this.mobileInput.getText());
			employee.setBloodGroupId(this.bloodGroupInput.getValue().getBloodGroupId());
			employee.setConsultantId(this.consultantInput.getValue().getDoctorId());
			employee.setRegisteredOn(Date.valueOf(this.registeredOnInput.getValue()));

			EmployeeData.save(employee);
		} catch (DBException e) {
			Global.log(e.getMessage());
		} catch (Exception e) {
			Global.log(e.getMessage());
		}
	}

}
