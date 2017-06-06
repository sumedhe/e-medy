package com.sumedhe.emedy;
	
import java.util.EventObject;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.config.Prefs;
import com.sumedhe.emedy.controller.HomeController;
import com.sumedhe.emedy.data.AdmissionData;
import com.sumedhe.emedy.data.AdmissionTestData;
import com.sumedhe.emedy.data.AdmissionTreatmentData;
import com.sumedhe.emedy.data.AttendanceData;
import com.sumedhe.emedy.data.BloodGroupData;
import com.sumedhe.emedy.data.BranchData;
import com.sumedhe.emedy.data.DesignationData;
import com.sumedhe.emedy.data.DoctorData;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.data.EmployeeWardData;
import com.sumedhe.emedy.data.PatientData;
import com.sumedhe.emedy.data.TestData;
import com.sumedhe.emedy.data.TreatmentData;
import com.sumedhe.emedy.data.WardData;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		// Load the main controller //
		try {
			HomeController homeController = new HomeController();
			
			primaryStage.setScene(new Scene(homeController));
			primaryStage.getScene().getStylesheets().add("/com/sumedhe/emedy/application.css");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("E-medy - Hospital Management System");
			primaryStage.setWidth(300);
			primaryStage.setHeight(200);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	                Platform.exit();
	                System.exit(0);
	            }
	        });
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Main //
	public static void main(String[] args) {
		configure();
		launch(args);
	}
	
	// Configure the environment //
	private static void configure(){
		
		Prefs.load();
		
		// Update all caches
		BloodGroupData.updateCache();
		BranchData.updateCache();
		DesignationData.updateCache();
		TestData.updateCache();
		TreatmentData.updateCache();
		WardData.updateCache();
		PatientData.updateCache();
		EmployeeData.updateCache();
		DoctorData.updateCache();
		AdmissionData.updateCache();
		EmployeeWardData.updateCache();
		AdmissionTreatmentData.updateCache();
		AdmissionTestData.updateCache();
		AttendanceData.updateCache();
		
		EmployeeData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				DoctorData.updateCache();
			}
		});
		
	
	}
}
