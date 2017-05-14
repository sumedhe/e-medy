package com.sumedhe.emedy.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class PatientController extends BorderPane implements IController {

	String url = "/com/sumedhe/emedy/view/PatientView.fxml";

	public PatientController() {
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
