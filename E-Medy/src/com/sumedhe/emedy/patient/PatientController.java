package com.sumedhe.emedy.patient;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sumedhe.emedy.common.CacheEventListener;
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

public class PatientController extends AnchorPane implements ITable {

	@FXML
	TableView<Patient> table;

	@FXML
	Button newButton, deleteButton, editButton;

	@FXML
	TextField searchInput;

	ObservableList<Patient> tableData = FXCollections.observableArrayList();

	static Timer timer = new Timer();

	// Constructor
	public PatientController() {
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
		PatientData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadData();
			}
		});
		newButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(new PatientEditController(new Patient(), this));
		});
		deleteButton.setOnAction(e -> {
			ButtonType result = Tool.showConfirmation(String.format("Do you want to delete %s?", getSelected()),
					"Delete");
			if (result == ButtonType.YES) {
				try {
					PatientData.delete(getSelected().getPatientId());
				} catch (DBException e1) {
					Tool.showError(String.format("Cannot delete %s!", getSelected()), "Delete");
					Global.logError(e1.getMessage());
				}
				loadData();
			}
		});
		editButton.setOnAction(e -> {
			if (getSelected() != null) {
				Global.getHome().setWorkPanel(new PatientEditController(getSelected(), this));
			}
		});
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				Global.getHome().setWorkPanel(new PatientEditController(getSelected(), this));
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
			if (e.getCode() == KeyCode.ENTER) {
				loadData();
			}
		});
		
	}

	// Configure the table, Create columns and set data collection
	@Override
	public void configTable() {
		String[][] colNames = { { "Name", "name" }, { "NIC", "nic" }, { "Date of Birth", "dob" },
				{ "Gender", "gender" }, { "Address", "address" }, { "Phone", "phone" } };

		for (String[] colName : colNames) {
			TableColumn<Patient, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			table.getColumns().add(col);
		}

		table.setItems(tableData);
		
	}

	// Load data to the table by a search string
	@Override
	public void loadData() {
		int sel = getSelected() == null ? -1 : getSelected().getPatientId();
		try {
			List<Patient> list = searchInput.getText().equals("") ? PatientData.getList()
					: PatientData.getBySearch(searchInput.getText());			
			tableData.removeAll(tableData);
			for (Patient p : list) {
				tableData.add(p);
			}
		} catch (DBException e) {
			Global.log(e.getMessage());
		}

		if (sel > 0) {
			setSelected(sel);
		}
	}

	// Get the selected object of the table
	public Patient getSelected() {
		return table.getSelectionModel().getSelectedItem();
	}

	// Select a row by it's ID
	public void setSelected(int patientId) {
		table.getItems().forEach(p -> {
			if (p.getPatientId() == patientId) {
				table.getSelectionModel().select(p);
			}
		});
	}

}
