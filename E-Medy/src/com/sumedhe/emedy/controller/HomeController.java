package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.common.NotificationType;
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
	Button dashboardButton, admissionButton, patientButton, employeeButton, wardButton, miscButton;

	@FXML
	VBox buttonBox;

	@FXML
	Button closeButton, testBtn;

	@FXML
	Pane notificationPane;

	@FXML
	Label notificationLabel;

	static Timer timer = new Timer();

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

	@Override
	public void initialize() {
		Global.setHome(this);
		panelList.put("Dashboard", new DashboardController());
		panelList.put("Admission", new AdmissionController());
		panelList.put("Patient", new PatientController());
		panelList.put("Employee", new EmployeeController());
		panelList.put("Ward", new WardController());
		panelList.put("Misc", new MiscController());

		setHandlers();

		// **** TMP ****
		setWorkPanel((Node) panelList.get("Admission"));
	}

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

		// test
		testBtn.setOnAction(e -> {
			test();
		});
	}

	public void setWorkPanel(Node panel) {
		workPane.getChildren().clear();
		AnchorPane.setTopAnchor(panel, 0.00);
		AnchorPane.setRightAnchor(panel, 0.00);
		AnchorPane.setBottomAnchor(panel, 0.00);
		AnchorPane.setLeftAnchor(panel, 0.00);
		workPane.getChildren().add(panel);
	}

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

	// !!! test !!!

	public void test() {
		// notificationPane.setVisible(false);
		Global.showNotification("Deleted...", NotificationType.Error);
		// Global.showNotification("Hello", NotificationType.Success);
		// Designation d = new Designation("Doctor", 250000);
		//
		// try {
		// DesignationData.save(d);
		// } catch (DBException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Map<Integer, Ward> list = new HashMap<Integer, Ward>();
		// list.put(1, new Ward("ward 1", 100));
		// list.put(2, new Ward("ward 2", 200));
		// Ward w = list.get(1);
		// Global.log(w.toString());
		// list.put(1, new Ward("ward 1 new", 1000));
		// Global.log(w.toString());

		// List<Ward> al = list.values().stream().filter(e ->
		// e.getName().contains("ard 1")).collect(Collectors.toList());
		// for (Ward w:al){
		// Global.log(w.getName());
		// }

		// Global.log("aa");
		// Global.log("--");
		// Global.log("!!" + DesignationData.getById(1).getName());
		// Global.log("!!" + DesignationData.getById(1).getName());
		// Global.log("--");
		// List<Designation> list = DesignationData.getList();
		// for (Designation d : list){
		// Global.log(d.toString());
		//// d.setName(d.getName() + " 1");
		//// DesignationData.save(d);
		// }
		// Global.log("eee");
		// Global.log(DesignationData.getById(1).toString());
		//

		// Global.log(String.format("sdfsa %s%%", "123"));

		// Map<Integer, String> cache = new HashMap<Integer, String>();
		// cache.put(1, "One");
		// cache.put(2, "Two");
		// cache.put(2, "New Two");
		//
		// Global.log(cache.get(4));
		// Global.log(Integer.toString(cache.keySet().size()));

	}

}