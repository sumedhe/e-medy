package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.common.Tabular;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.model.Employee;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Tool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class EmployeeController  extends AnchorPane implements Tabular{

	@FXML
	TableView<Employee> table;
	
	@FXML
	Button newButton, deleteButton, editButton;
	
	@FXML
	TextField searchInput;
	
	ObservableList<Employee> tableData = FXCollections.observableArrayList();
	
	static Timer timer = new Timer();
	
	public EmployeeController() {
		String url = "/com/sumedhe/emedy/view/TableView.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}


	// Initialization
	@Override
	public void initialize() {
		setHandlers();
		configTable();
		loadData();
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		EmployeeData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadData();
			}
		});
		newButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(new EmployeeEditController(new Employee(), this));	
		});
		deleteButton.setOnAction(e -> {
			ButtonType result = Tool.showConfirmation(String.format("Do you want to delete %s?", getSelected()), "Delete");
			if (result == ButtonType.YES) {
				try {
					EmployeeData.delete(getSelected().getEmployeeId());
					Global.showNotification("Deleted...", NotificationType.Information);
				} catch (DBException e1) {
					Global.showNotification(String.format("Cannot delete %s!", getSelected()), NotificationType.Error);
					Global.logError(e1.getMessage());
				}
				loadData();
			}
		});
		editButton.setOnAction(e -> {
			if (getSelected() != null){
				Global.getHome().setWorkPanel(new EmployeeEditController(getSelected(), this));				
			}
		});
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2){
				Global.getHome().setWorkPanel(new EmployeeEditController(getSelected(), this));
			}
		});
		searchInput.textProperty().addListener(e -> {
			timer.cancel();
			timer.purge();
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					table.setVisible(false);
					loadData();
					table.setVisible(true);
				}
			}, Global.getSearchInterval());
		});
		searchInput.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER){
				loadData();
			}
		});
	}

	// Configure the table, Create columns and set data collection
	@Override
	public void configTable() {		
		String[][] colNames = {{"Name", "name"}, {"NIC", "nic"},
				{"Date of Birth", "dob"}, {"Gender", "gender"},
				{"Address", "address"}, {"Phone", "phone"},
				{"StartDate", "startDate"}, {"Designation", "designation"}};
		
		for (String[] colName : colNames){
			TableColumn<Employee, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			table.getColumns().add(col);			
		}

		table.setItems(tableData);
	}

	// Load data to the table by a search string
	@Override
	public void loadData() {
		int sel = getSelected() == null ? -1 : getSelected().getEmployeeId();
		try {
			List<Employee> list = searchInput.getText().equals("") ? EmployeeData.getList()
					: EmployeeData.getBySearch(searchInput.getText());			
			tableData.removeAll(tableData);
			for (Employee i : list) {
				tableData.add(i);
			}
		} catch (DBException e) {
			Global.log(e.getMessage());
		}

		if (sel > 0) {
			setSelected(sel);
		}
	}
	
	// Get the selected object of the table
	public Employee getSelected(){
		return table.getSelectionModel().getSelectedItem();
	}
	
	// Select a row by it's ID
	public void setSelected(int employeeId){
		table.getItems().forEach(e -> {
			if (e.getEmployeeId() == employeeId){
				table.getSelectionModel().select(e);
			}
		});
	}
	

}
