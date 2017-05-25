package com.sumedhe.emedy.dashboard;

import java.io.IOException;

import com.sumedhe.emedy.common.IController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class DashboardController extends AnchorPane implements IController{
	
	public DashboardController(){
		String url = "/com/sumedhe/emedy/dashboard/DashboardView.fxml";
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
		// TODO Auto-generated method stub
	}

	@Override
	public void setHandlers() {
		// TODO Auto-generated method stub
	}
	
}
