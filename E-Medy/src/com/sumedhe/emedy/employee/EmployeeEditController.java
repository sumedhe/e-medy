package com.sumedhe.emedy.employee;

import java.io.IOException;
import java.sql.Date;
import java.util.EventObject;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.ComboBoxFilterListener;
import com.sumedhe.emedy.common.Gender;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.common.IController;
import com.sumedhe.emedy.common.Validator;
import com.sumedhe.emedy.common.ValidatorEvent;
import com.sumedhe.emedy.misc.Branch;
import com.sumedhe.emedy.misc.BranchData;
import com.sumedhe.emedy.misc.Designation;
import com.sumedhe.emedy.misc.DesignationData;
import com.sumedhe.emedy.service.DBException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
	ComboBox<Branch> branchInput;

	@FXML
	Button backButton, saveButton, saveAndNewButton;
	
	@FXML
	Label branchLabel;

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
		branchInput.getItems().addAll(BranchData.getList());
		new ComboBoxFilterListener<Gender>(genderInput);
		new ComboBoxFilterListener<Designation>(designationInput);
		new ComboBoxFilterListener<Branch>(branchInput);

		setHandlers();
		setEmployee(employee);
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		branchLabel.visibleProperty().bind(branchInput.visibleProperty());
		DesignationData.getCache().addCacheEventListener(new CacheEventListener() {			
			@Override
			public void updated(EventObject e) {
				designationInput.getItems().clear();
				designationInput.getItems().addAll(DesignationData.getList());
			}
		});
		BranchData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				branchInput.getItems().clear();
				branchInput.getItems().addAll(BranchData.getList());
			}
		});
		designationInput.setOnAction(e -> {
			branchInput.setVisible(designationInput.getValue().isDoctor());
		});
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
		validator.addToCheckNull(designationInput);

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
		this.startDateInput.setValue(employee.getStartDate().toLocalDate());
		this.designationInput.setValue(employee.getDesignation());
		if (employee.getDesignation() != null && employee.getDesignation().isDoctor()){
			branchInput.setValue(DoctorData.getByEmployeeId(employee.getEmployeeId()).getBranch());
			branchInput.setVisible(true);
		} else {
			branchInput.setVisible(false);
		}
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
			employee.setStartDate(Date.valueOf(this.startDateInput.getValue().toString()));
			employee.setDesignationId(this.designationInput.getValue().getDesignationId());

			if (this.designationInput.getValue().isDoctor()){
				Doctor d = new Doctor(employee, this.branchInput.getValue().getBranchId());
				DoctorData.save(d);
			} else {				
				EmployeeData.save(employee);
			}
			
		} catch (DBException e) {
			Global.log(e.getMessage());
		} catch (Exception e) {
			Global.log(e.getMessage());
		}
	}

}
