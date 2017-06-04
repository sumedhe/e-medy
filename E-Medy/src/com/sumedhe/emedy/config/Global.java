package com.sumedhe.emedy.config;

import java.util.Timer;
import java.util.TimerTask;

import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.controller.HomeController;

import javafx.scene.layout.Pane;

public class Global {
	
	static HomeController home;
	static int searchInterval = 200;
	static int notificationInterval = 2000;
	static Timer timer = new Timer();
	
	public static HomeController getHome() {
		return home;
	}
	
	public static void setHome(HomeController home) {
		Global.home = home;
	}

    public static void log(String message){
        System.out.println(message);
    }
    
    public static void logError(String message){
    	System.out.println("[ERROR] " + message);
    }
    
    public static void logError(String message, String notification){
    	logError(message);
    	showNotification(notification, NotificationType.Error);
    }
    
    
    public static int getSearchInterval() {
		return searchInterval;
	}
    
    
   
    
    public static void showNotification(String message, NotificationType type){
    	getHome().getNotificationLabel().setText(message);
    	Pane pane = getHome().getNotificationPane();
    	if (type == NotificationType.Success){
    		pane.getStyleClass().add("notification-success");
    	} else if (type == NotificationType.Information) {
    		pane.getStyleClass().add("notification-information");
    	} else if (type == NotificationType.Error) {
    		pane.getStyleClass().add("notification-error");
    	}
    	pane.setVisible(true);
    	timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				pane.setVisible(false);
				pane.getStyleClass().clear();
			}
		}, notificationInterval);
    }
    
    
    
}
