package com.sumedhe.emedy.employee;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.sumedhe.emedy.common.Global;
import com.sumedhe.emedy.common.ITable;
import com.sumedhe.emedy.common.Tool;
import com.sumedhe.emedy.service.DBException;

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

public class EmployeeController  extends AnchorPane implements ITable{

	@FXML
	TableView<Employee> table;
	
	@FXML
	Button newButton, deleteButton, editButton;
	
	@FXML
	TextField searchInput;
	
	ObservableList<Employee> tableData = FXCollections.observableArrayList();
	
	static Timer timer = new Timer();
	
	public EmployeeController() {
		String url = "/com/sumedhe/emedy/common/TableView.fxml";
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
		newButton.setOnAction(e -> {

		});
		deleteButton.setOnAction(e -> {
			ButtonType result = Tool.showConfirmation(String.format("Do you want to delete %s?", getSelected()), "Delete");
			if (result == ButtonType.YES) {
				try {
					EmployeeData.delete(getSelected().getEmployeeId());
				} catch (DBException e1) {
					Tool.showError(String.format("Cannot delete %s!", getSelected()), "Delete");
					Global.logError(e1.getMessage());
				}
				loadData();
			}
		});
		editButton.setOnAction(e -> {
//			if (getSelected() != null){
//				Global.getHome().setWorkPanel(new EmployeeEditController(getSelected(), this));				
//			}
		});
		table.setOnMouseClicked(e -> {
//			if (e.getClickCount() == 2){
//				Global.getHome().setWorkPanel(new EmployeeEditController(getSelected(), this));
//			}
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
//		int sel = getSelected() == null ? -1 : getSelected().getEmployeeId();
//		tableData.removeAll(tableData);
//		try {
//			for (Employee p : EmployeeData.getList(searchInput.getText())){
//				tableData.add(p);
//			}
//		} catch (DBException e) {
//			Global.log(e.getMessage());
//		}
//
//		if (sel > 0) { setSelected(sel); }
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
