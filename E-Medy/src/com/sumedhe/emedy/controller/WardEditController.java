package com.sumedhe.emedy.controller;

import java.io.IOException;

import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.WardData;
import com.sumedhe.emedy.model.Ward;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Validator;
import com.sumedhe.emedy.tool.ValidatorEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class WardEditController extends AnchorPane implements Controllable {

	@FXML
	TextField wardNameInput, wardNoInput, maxPatientsInput;

	@FXML
	Button backButton, saveButton, saveAndNewButton;

	Node prev;
	Ward ward;
	Validator validator = new Validator();

	// Constructor
	public WardEditController(Ward ward, Node prev) {
		this.prev = prev;
		this.ward = ward;

		String url = "/com/sumedhe/emedy/view/WardEditView.fxml";
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
		setHandlers();
		setWard(ward);
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
			}
		});
		saveAndNewButton.setOnAction(e -> {
			if (validator.checkValidity(new ValidatorEvent(this))) {
				save();
				setWard(new Ward());
			}
		});

		// Set handlers for Validation Checking
		validator.addToCheckEmpty(wardNameInput);
		validator.addToCheckEmpty(wardNoInput);
		validator.addToCheckNumeric(wardNoInput);
		validator.addToCheckEmpty(maxPatientsInput);
		validator.addToCheckNumeric(maxPatientsInput);
	}

	// Fill the form from the given object
	public void setWard(Ward ward) {
		this.ward = ward;
		this.wardNameInput.setText(ward.getName());
		this.wardNoInput.setText(Integer.toString(ward.getWardNo()));
		this.maxPatientsInput.setText(Integer.toString(ward.getMaxPatients()));
	}

	// Create a object from the form and save it
	public void save() {
		
		try {
			ward.setName(this.wardNameInput.getText());
			ward.setWardNo(Integer.parseInt(this.wardNoInput.getText()));
			ward.setMaxPatients(Integer.parseInt(this.maxPatientsInput.getText()));

			Ward tmp = WardData.getByWardNo(Integer.parseInt(this.wardNoInput.getText()));
			if (tmp != null && tmp.getWardId() != ward.getWardId()){
				Global.showNotification("Ward no already exists!", NotificationType.Error);
				return;
			}
			
			WardData.save(ward);
			Global.showNotification("Saved...", NotificationType.Success);
		} catch (DBException e) {
			Global.log(e.getMessage());
		} catch (Exception e) {
			Global.log(e.getMessage());
		}
	}

}
