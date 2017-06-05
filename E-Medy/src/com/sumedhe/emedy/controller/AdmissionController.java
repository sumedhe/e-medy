package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.EventObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.common.Tabular;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.AdmissionData;
import com.sumedhe.emedy.model.Admission;
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

public class AdmissionController extends AnchorPane implements Tabular {

	@FXML
	TableView<Admission> table;

	@FXML
	Button newButton, deleteButton, editButton, dischargeButton, treatmentButton;

	@FXML
	TextField searchInput;

	ObservableList<Admission> tableData = FXCollections.observableArrayList();

	static Timer timer = new Timer();

	// Constructor
	public AdmissionController() {
		String url = "/com/sumedhe/emedy/view/AdmissionView.fxml";
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
		AdmissionData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadData();
			}
		});
		newButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(new AdmissionEditController(new Admission(), this));
		});
		deleteButton.setOnAction(e -> {
			ButtonType result = Tool.showConfirmation(
					String.format("Do you want to delete the admission of %s?", getSelected().getPatient()), "Delete");
			if (result == ButtonType.YES) {
				try {
					AdmissionData.delete(getSelected().getAdmissionId());
					Global.showNotification("Deleted...", NotificationType.Information);
				} catch (DBException e1) {
					Global.showNotification(String.format("Cannot delete %s!", getSelected()), NotificationType.Error);
					Global.logError(e1.getMessage());
				}
				loadData();
			}
		});
		editButton.setOnAction(e -> {
			if (getSelected() != null) {
				Global.getHome().setWorkPanel(new AdmissionEditController(getSelected(), this));
			}
		});
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				Global.getHome().setWorkPanel(new AdmissionEditController(getSelected(), this));
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
		dischargeButton.setOnAction(e -> {
			Admission a = getSelected();
			if (a != null && !a.getIsDischarged()) {
				if (Tool.showConfirmation("Do you want to discharge " + a.getPatient().getName() + "?",
						"Discharge") == ButtonType.YES) {
					a.setDischarged(true);
					a.setDischargedOn(new Date(Calendar.getInstance().getTimeInMillis()));
					try {
						AdmissionData.save(a);
					} catch (DBException e1) {
						Global.showNotification("Error in discharge", NotificationType.Error);
						Global.logError(e1.getMessage());
					}
				}
			}
		});
		treatmentButton.setOnAction(e -> {
			if (getSelected() != null){
				Global.getHome().setWorkPanel(new AdmissionTreatmentController(getSelected(), this));				
			}
		});
	}

	// Configure the table, Create columns and set data collection
	@Override
	public void configTable() {
		String[][] colNames = { { "Date", "admittedOn" }, { "Patient", "patient" },
				{ "Doctor Confirmed", "confirmedDoctor" }, { "Custodian", "custodianName" },
				{ "State", "state" } };

		for (String[] colName : colNames) {
			TableColumn<Admission, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			table.getColumns().add(col);
		}

		table.setItems(tableData);

	}

	// Load data to the table by a search string
	@Override
	public void loadData() {
		int sel = getSelected() == null ? -1 : getSelected().getAdmissionId();
		try {
			List<Admission> list = searchInput.getText().equals("") ? AdmissionData.getList()
					: AdmissionData.getBySearch(searchInput.getText());
			tableData.removeAll(tableData);
			for (Admission p : list) {
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
	public Admission getSelected() {
		return table.getSelectionModel().getSelectedItem();
	}

	// Select a row by it's ID
	public void setSelected(int admissionId) {
		table.getItems().forEach(p -> {
			if (p.getAdmissionId() == admissionId) {
				table.getSelectionModel().select(p);
			}
		});
	}

}
