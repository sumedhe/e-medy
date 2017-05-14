package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sumedhe.emedy.Global;
import com.sun.prism.paint.Color;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController extends BorderPane implements IController {

	String url = "/com/sumedhe/emedy/view/MainView.fxml";
	Map<String, Object> panelList = new HashMap<String, Object>();

	@FXML
	Pane desktopPane;

	@FXML
	Button dashboardButton, admissionButton, patientButton, employeeButton;
	
	@FXML
	VBox buttonBox;

	public MainController() {
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
		panelList.put("Dashboard", new DashboardController());
		panelList.put("Admission", new AdmissionController());
		panelList.put("Patient", new PatientController());
		panelList.put("Employee", new EmployeeController());

		setHandlers();
	}

	@Override
	public void setHandlers() {
		dashboardButton.setOnAction(e -> {
			showPanel("Dashboard");
			//dashboardButton.getStyleClass().remove("menu-button");
//			dashboardButton.getStyleClass().add("menu-button-selected");
			selectButton(dashboardButton);
			
		});
		admissionButton.setOnAction(e -> {
			showPanel("Admission");
			selectButton(admissionButton);
		});
		patientButton.setOnAction(e -> {
			showPanel("Patient");
			selectButton(patientButton);
		});
		employeeButton.setOnAction(e -> {
			showPanel("Employee");
			selectButton(employeeButton);
		});
	}

	public void showPanel(String panelName) {
		desktopPane.getChildren().clear();
		desktopPane.getChildren().add((Node) panelList.get(panelName));
	}
	
	public void selectButton(Button button){
		ObservableList<Node> children2 = buttonBox.getChildren();
		for (int i = 0; i < children2.size(); i++) {
			Node obj = children2.get(i);
			if (obj instanceof Button){
				obj.getStyleClass().remove("menu-button-selected");
			}
		}
		
		button.getStyleClass().add("menu-button-selected");
	}

}
