package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.EventObject;
import java.util.List;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.ComboBoxFilterListener;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.common.Tabular;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.AttendanceData;
import com.sumedhe.emedy.data.EmployeeData;
import com.sumedhe.emedy.model.Attendance;
import com.sumedhe.emedy.model.Employee;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Tool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AttendanceController extends AnchorPane implements Tabular {

	@FXML
	TableView<Attendance> table;

	@FXML
	Button addButton, deleteButton;

	@FXML
	ComboBox<Employee> employeeInput;

	@FXML
	DatePicker dateInput;

	ObservableList<Attendance> tableData = FXCollections.observableArrayList();

	// Constructor
	public AttendanceController() {
		String url = "/com/sumedhe/emedy/view/AttendanceView.fxml";
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
		// Prepare the components //
		employeeInput.getItems().addAll(EmployeeData.getList());
		new ComboBoxFilterListener<Employee>(employeeInput);
		
		setHandlers();
		configTable();
		loadData();
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		AttendanceData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadData();
			}
		});
		EmployeeData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				int sel = employeeInput.getValue() != null ? employeeInput.getValue().getEmployeeId() : -1;
				employeeInput.getItems().clear();
				employeeInput.getItems().addAll(EmployeeData.getList());
				for (Employee em : employeeInput.getItems()){
					if (em.getEmployeeId() == sel){
						employeeInput.setValue(em);
						return;
					}
				}
			}
		});

		deleteButton.setOnAction(e -> {
			if (getSelected() == null) {
				return;
			}
			
			ButtonType result = Tool.showConfirmation("Do you want to delete entry?", "Delete");
			if (result == ButtonType.YES) {
				try {
					AttendanceData.delete(getSelected().getAttendanceId());
					Global.showNotification("Deleted...", NotificationType.Information);
				} catch (DBException e1) {
					Global.showNotification("Cannot delete the entry", NotificationType.Error);
					Global.logError(e1.getMessage());
				}
				loadData();
			}
		});
		addButton.setOnAction(e -> {
			if (this.employeeInput.getValue() != null && this.dateInput.getValue() != null) {
				Attendance a = new Attendance();
				a.setDate(Date.valueOf(this.dateInput.getValue().toString()));
				a.setEmployeeId(this.employeeInput.getValue().getEmployeeId());
				
				for (Attendance at : table.getItems()){
					if (at.getEmployeeId() == a.getEmployeeId()){
						Global.showNotification("Already added!", NotificationType.Information);
						return;
					}
				}
				
				try {
					AttendanceData.save(a);
				} catch (DBException e1) {
					Global.logError(e1.getMessage(), "Error in add entry");
				}
			} else {
				Global.showNotification("Please select a date and an employee to add", NotificationType.Error);
			}
		});
		dateInput.setOnAction(e -> {
			loadData();
		});
		employeeInput.setOnAction(e -> {
			loadData();
		});

	}

	// Configure the table, Create columns and set data collection
	@Override
	public void configTable() {
		String[][] colNames = { { "Name", "employeeName" }, { "Date", "date" } };

		for (String[] colName : colNames) {
			TableColumn<Attendance, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			table.getColumns().add(col);
		}

		table.setItems(tableData);

	}

	// Load data to the table by a search string
	@Override
	public void loadData() {
		int sel = getSelected() == null ? -1 : getSelected().getAttendanceId();
		try {
			List<Attendance> list = dateInput.getValue() == null ? AttendanceData.getList()
					: AttendanceData.getByDate(Date.valueOf(dateInput.getValue().toString()));
			tableData.removeAll(tableData);
			for (Attendance p : list) {
				tableData.add(p);
			}
		} catch (Exception e) {
			Global.log(e.getMessage());
		}

		if (sel > 0) {
			setSelected(sel);
		}
	}

	// Get the selected object of the table
	public Attendance getSelected() {
		return table.getSelectionModel().getSelectedItem();
	}

	// Select a row by it's ID
	public void setSelected(int patientId) {
		table.getItems().forEach(p -> {
			if (p.getAttendanceId() == patientId) {
				table.getSelectionModel().select(p);
			}
		});
	}

}
