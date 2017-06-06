package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.EventObject;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.data.AdmissionData;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.data.WardData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DashboardController extends AnchorPane implements Controllable{
	
	@FXML
	Label patientCountLabel, wardCountLabel, employeeCountLabel;
	
	// Constructor //
	public DashboardController(){
		String url = "/com/sumedhe/emedy/view/DashboardView.fxml";
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
		loadInfo();
	}

	// Setting handlers //
	@Override
	public void setHandlers() {
		AdmissionData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadInfo();
			}
		});
		WardData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadInfo();
			}
		});
		EmployeeData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadInfo();
			}
		});
	}
	
	// Loading Dashboard information //
	public void loadInfo(){
		patientCountLabel.setText(Long.toString(AdmissionData.getCache().getStream().filter(x -> x.getIsDischarged() != false).count()));
		wardCountLabel.setText(Long.toString(WardData.getList().size()));
		employeeCountLabel.setText(Long.toString(EmployeeData.getList().size()));
	}
	
}
