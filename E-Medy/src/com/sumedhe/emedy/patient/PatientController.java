package com.sumedhe.emedy.patient;

import java.io.IOException;
import java.util.Optional;

import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.common.ITable;
import com.sumedhe.emedy.service.DBException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PatientController extends AnchorPane implements  ITable {

	@FXML
	TableView<Patient> table;
	
	@FXML
	Button newButton, deleteButton, editButton;
	
    
	public PatientController() {
		String url = "/com/sumedhe/emedy/patient/PatientView.fxml";
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
		setHandlers();
		configTable();
		loadData();
	}

	@Override
	public void setHandlers() {
		newButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(new PatientEditController(new Patient(), this));
		});
		deleteButton.setOnAction(e -> {
			Alert a = new Alert(AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
			a.setTitle("Delete");
			a.setHeaderText("Do you want to delete " + getSelected().getName() + "?");
			Optional<ButtonType> result = a.showAndWait();
			if (result.get() == ButtonType.YES) {
				try {
					PatientData.delete(getSelected().getPatientId());
				} catch (DBException e1) {
					Global.log(e1.getMessage());
				}
				loadData();
			}
			
		});
		editButton.setOnAction(e -> {
			if (getSelected() != null){
				Global.getHome().setWorkPanel(new PatientEditController(getSelected(), this));				
			}
		});
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2){
				Global.getHome().setWorkPanel(new PatientEditController(getSelected(), this));
			}
		});

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
		int sel = getSelected() == null ? -1 : getSelected().getPatientId();
		table.getItems().clear();
		try {
			for (Patient p : PatientData.getList()){
				table.getItems().add(p);
			}
		} catch (DBException e) {
			Global.log(e.getMessage());
		}
		if (sel > 0) { setSelected(sel); }
	}
	
	public Patient getSelected(){
		return table.getSelectionModel().getSelectedItem();
	}
	
	public void setSelected(int patientId){
		table.getItems().forEach(p -> {
			if (p.getPatientId() == patientId){
				table.getSelectionModel().select(p);
			}
		});
	}
	

	
	

}


