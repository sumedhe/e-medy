package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController extends AnchorPane implements IController {

	Map<String, Object> panelList = new HashMap<String, Object>();

	@FXML
	AnchorPane desktopPane;

	@FXML
	Button dashboardButton, admissionButton, patientButton, employeeButton, miscButton;

	@FXML
	VBox buttonBox;

	public MainController() {
		String url = "/com/sumedhe/emedy/view/MainView.fxml";
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
		panelList.put("Misc", new PatientEditController());
		
		configure();
	}

	@Override
	public void configure() {
		dashboardButton.setOnAction(e -> {
			showPanel("Dashboard");
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
		miscButton.setOnAction(e -> {
			showPanel("Misc");
			selectButton(miscButton);
		});
	}

	public void showPanel(String panelName) {
		desktopPane.getChildren().clear();
		Node panel = (Node) panelList.get(panelName);
		AnchorPane.setTopAnchor(panel, 0.00);
		AnchorPane.setRightAnchor(panel, 0.00);
		AnchorPane.setBottomAnchor(panel, 0.00);
		AnchorPane.setLeftAnchor(panel, 0.00);
		desktopPane.getChildren().add(panel);
	}

	public void selectButton(Button button) {
		ObservableList<Node> children2 = buttonBox.getChildren();
		for (int i = 0; i < children2.size(); i++) {
			Node obj = children2.get(i);
			if (obj instanceof Button) {
				obj.getStyleClass().remove("menu-button-selected");
			}
		}

		button.getStyleClass().add("menu-button-selected");
	}

}
