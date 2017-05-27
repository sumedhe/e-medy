package com.sumedhe.emedy;
	
import com.sumedhe.emedy.home.HomeController;

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
		
		try {
			HomeController homeController = new HomeController();
//			PatientController homeController = new PatientController();
			
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
	
	public static void main(String[] args) {
		
		configure();
		
		launch(args);
	}
	
	private static void configure(){
		
		Prefs.load();
		
		
	
	}
}
