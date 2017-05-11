package com.sumedhe.emedy.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainController {
	
	@FXML
	Pane desktopPane;
	
	@FXML
	Button dashboardButton, patientButton;
	
	
	public void initialize(){
		
			dashboardButton.setOnAction(e -> { showPanel("DASHBOARD"); } );
	
	}
	
	public void showPanel(String panelName){
		
		desktopPane.getChildren().clear();
		
		switch (panelName){
		case "DASHBOARD":
			try {
				desktopPane.getChildren().add(FXMLLoader.load(getClass().getResource("/com/sumedhe/emedy/view/DashboardView.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

}