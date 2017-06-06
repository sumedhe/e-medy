package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.config.Global;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeController extends AnchorPane implements Controllable {

	Map<String, Object> panelList = new HashMap<String, Object>();

	@FXML
	AnchorPane workPane;

	@FXML
	Button dashboardButton, admissionButton, patientButton, employeeButton, attendanceButton, wardButton, miscButton;

	@FXML
	VBox buttonBox;

	@FXML
	Button closeButton;

	@FXML
	Pane notificationPane;

	@FXML
	Label notificationLabel;

	static Timer timer = new Timer();

	// Constructor //
	public HomeController() {
		String url = "/com/sumedhe/emedy/view/HomeView.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	// Initialization //
	@Override
	public void initialize() {
		Global.setHome(this);
		panelList.put("Dashboard", new DashboardController());
		panelList.put("Admission", new AdmissionController());
		panelList.put("Patient", new PatientController());
		panelList.put("Employee", new EmployeeController());
		panelList.put("Attendance", new AttendanceController());
		panelList.put("Ward", new WardController());
		panelList.put("Misc", new MiscController());

		setHandlers();

		// Set first page
		setWorkPanel((Node) panelList.get("Dashboard"));
		selectButton(dashboardButton);
	}

	// Setting Handlers //
	@Override
	public void setHandlers() {
		dashboardButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Dashboard"));
			selectButton(dashboardButton);
		});
		admissionButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Admission"));
			selectButton(admissionButton);
		});
		patientButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Patient"));
			selectButton(patientButton);
		});
		employeeButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Employee"));
			selectButton(employeeButton);
		});
		attendanceButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Attendance"));
			selectButton(attendanceButton);
		});		
		wardButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Ward"));
			selectButton(wardButton);
		});
		miscButton.setOnAction(e -> {
			setWorkPanel((Node) panelList.get("Misc"));
			selectButton(miscButton);
		});
		closeButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

	}

	// Set a panel to the workspace //
	public void setWorkPanel(Node panel) {
		workPane.getChildren().clear();
		AnchorPane.setTopAnchor(panel, 0.00);
		AnchorPane.setRightAnchor(panel, 0.00);
		AnchorPane.setBottomAnchor(panel, 0.00);
		AnchorPane.setLeftAnchor(panel, 0.00);
		workPane.getChildren().add(panel);
	}

	// Change the color when selecting a button //
	public void selectButton(Button button) {
		buttonBox.getChildren().stream().filter(x -> x instanceof Button)
				.forEach(x -> x.getStyleClass().remove("menu-button-selected"));
		button.getStyleClass().add("menu-button-selected");
	}

	// notification
	public Pane getNotificationPane() {
		return notificationPane;
	}

	public Label getNotificationLabel() {
		return notificationLabel;
	}

}
