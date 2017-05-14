package com.sumedhe.emedy;
	
import com.sumedhe.emedy.controller.MainController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			MainController mainController = new MainController();
			
			primaryStage.setScene(new Scene(mainController));
			primaryStage.getScene().getStylesheets().add("/com/sumedhe/emedy/application.css");
			primaryStage.setTitle("Custom Control");
			primaryStage.setWidth(300);
			primaryStage.setHeight(200);
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
