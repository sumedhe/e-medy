package com.sumedhe.emedy.home;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import com.sumedhe.emedy.admission.AdmissionController;
import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.common.IController;
import com.sumedhe.emedy.dashboard.DashboardController;
import com.sumedhe.emedy.employee.EmployeeController;
import com.sumedhe.emedy.patient.Patient;
import com.sumedhe.emedy.patient.PatientController;
import com.sumedhe.emedy.patient.PatientEditController;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HomeController extends AnchorPane implements IController {

	Map<String, Object> panelList = new HashMap<String, Object>();

	@FXML
	AnchorPane workPane;

	@FXML
	Button dashboardButton, admissionButton, patientButton, employeeButton, miscButton;

	@FXML
	VBox buttonBox;

	@FXML
	Button closeButton, testBtn;

	static Timer timer = new Timer();

	public HomeController() {
		String url = "/com/sumedhe/emedy/home/HomeView.fxml";
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
		panelList.put("Misc", new PatientEditController(new Patient(), null));

		setHandlers();

		// **** TMP ****
		setWorkPanel((Node) panelList.get("Patient"));
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
		ObservableList<Node> buttons = buttonBox.getChildren();
		for (int i = 0; i < buttons.size(); i++) {
			Node obj = buttons.get(i);
			if (obj instanceof Button) {
				obj.getStyleClass().remove("menu-button-selected");
			}
		}

		button.getStyleClass().add("menu-button-selected");
	}

	// !!! test !!!

	public void test() {

		Global.log(String.format("sdfsa %s%%", "123"));
		
		
//		timer.cancel();
//		timer.purge();
//		timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				Global.log("ss");
//			}
//		}, 200);
//		
		
		
		
		//		Global.log(Long.toString(searchTask.scheduledExecutionTime()));
//		if (searchTask != null & searchTask.scheduledExecutionTime() > 0) {
//			searchTask.cancel();
//		}
//		searchTask = new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("sss");
//			}
//		};
//		timer.schedule(searchTask, 2000);
	}

}
