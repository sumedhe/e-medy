package com.sumedhe.emedy;
	
import com.sumedhe.emedy.home.HomeController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			HomeController homeController = new HomeController();
			
			primaryStage.setScene(new Scene(homeController));
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
