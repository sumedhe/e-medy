package com.sumedhe.emedy.controller;

import java.io.IOException;

import com.sumedhe.emedy.model.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PatientController extends AnchorPane implements  ITable {

	@FXML
	TableView<Patient> table;
	
    
	public PatientController() {
		String url = "/com/sumedhe/emedy/view/PatientView.fxml";
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
		configure();
		configTable();
		loadData();
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void configTable() {		
		String[][] colNames = {{"Name", "name"}, {"NIC", "nic"},
				{"Date of Birth", "dob"}, {"Gender", "gender"},
				{"Address", "address"}, {"Phone", "phone"}};
		
		for (String[] colName : colNames){
			TableColumn<Patient, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			table.getColumns().add(col);			
		}
		
		ObservableList<Patient> data = FXCollections.observableArrayList();
		table.setItems(data);
	}

	@Override
	public void loadData() {
		configTable();
		Patient p = new Patient();
		p.setFirstName("dfsf");
		p.setNic("dfaddf");
		table.getItems().add(p);
		
	}
	
	

}


