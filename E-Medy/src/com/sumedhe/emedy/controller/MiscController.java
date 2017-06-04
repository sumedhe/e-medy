package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.EventObject;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.Controllable;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.BloodGroupData;
import com.sumedhe.emedy.data.BranchData;
import com.sumedhe.emedy.data.DesignationData;
import com.sumedhe.emedy.data.TestData;
import com.sumedhe.emedy.data.TreatmentData;
import com.sumedhe.emedy.model.BloodGroup;
import com.sumedhe.emedy.model.Branch;
import com.sumedhe.emedy.model.Designation;
import com.sumedhe.emedy.model.Test;
import com.sumedhe.emedy.model.Treatment;
import com.sumedhe.emedy.service.DBException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class MiscController extends AnchorPane implements Controllable {

	@FXML
	TableView<Designation> designationTable;

	@FXML
	TableView<Test> testTable;

	@FXML
	TableView<Treatment> treatmentTable;

	@FXML
	ListView<Branch> branchList;

	@FXML
	ListView<BloodGroup> bloodGroupList;

	@FXML
	Button designationSaveButton, designationAddButton, designationDeleteButton, testSaveButton, testAddButton,
			testDeleteButton, treatmentSaveButton, treatmentAddButton, treatmentDeleteButton, branchSaveButton,
			branchAddButton, branchDeleteButton, bloodGroupSaveButton, bloodGroupAddButton, bloodGroupDeleteButton;

	@FXML
	TextField designationNameInput, designationSalaryInput, testNameInput, testFeeInput, treatmentNameInput,
			treatmentFeeInput, branchNameInput, bloodGroupNameInput;

	ObservableList<Designation> designationData = FXCollections.observableArrayList();
	ObservableList<Test> testData = FXCollections.observableArrayList();
	ObservableList<Treatment> treatmentData = FXCollections.observableArrayList();

	// Constructor
	public MiscController() {
		String url = "/com/sumedhe/emedy/view/MiscView.fxml";
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
		loadDesignationData();
		loadTestData();
		loadTreatmentData();
		loadBranchData();
		loadBloodGroupData();
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		DesignationData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadDesignationData();
			}
		});
		TestData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadTestData();
			}
		});
		TreatmentData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadTreatmentData();
			}
		});
		BranchData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadBranchData();
			}
		});
		BloodGroupData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadBloodGroupData();
			}
		});

		designationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				designationNameInput.setText(newSelection.getName());
				designationSalaryInput.setText(Double.toString(newSelection.getWage()));
			}
		});
		testTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				testNameInput.setText(newSelection.getName());
				testFeeInput.setText(Double.toString(newSelection.getFee()));
			}
		});
		treatmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				treatmentNameInput.setText(newSelection.getName());
				treatmentFeeInput.setText(Double.toString(newSelection.getFee()));
			}
		});
		branchList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				branchNameInput.setText(newSelection.getName());
			}
		});
		bloodGroupList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				bloodGroupNameInput.setText(newSelection.getName());
			}
		});

		designationSaveButton.setOnAction(e -> {
			try {
				Designation i = designationTable.getSelectionModel().getSelectedItem();
				if (i != null) {
					i.setName(this.designationNameInput.getText());
					i.setWage(Double.parseDouble(this.designationSalaryInput.getText()));
					DesignationData.save(i);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Save could not be completed!");
			}
		});
		designationAddButton.setOnAction(e -> {
			Designation i = new Designation();
			designationData.add(i);
			designationTable.getSelectionModel().select(i);
		});
		designationDeleteButton.setOnAction(e -> {
			try {
				DesignationData.delete(designationTable.getSelectionModel().getSelectedItem().getDesignationId());
				Global.showNotification("Designaton deleted!", NotificationType.Success);
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

		testSaveButton.setOnAction(e -> {
			try {
				Test i = testTable.getSelectionModel().getSelectedItem();
				if (i != null) {
					i.setName(this.testNameInput.getText());
					i.setFee(Double.parseDouble(this.testFeeInput.getText()));
					TestData.save(i);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Save could not be completed!");
			}
		});
		testAddButton.setOnAction(e -> {
			Test i = new Test();
			testData.add(i);
			testTable.getSelectionModel().select(i);
		});
		testDeleteButton.setOnAction(e -> {
			try {
				TestData.delete(testTable.getSelectionModel().getSelectedItem().getTestId());
				Global.showNotification("Test deleted!", NotificationType.Success);
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

		treatmentSaveButton.setOnAction(e -> {
			try {
				Treatment i = treatmentTable.getSelectionModel().getSelectedItem();
				if (i != null) {
					i.setName(this.treatmentNameInput.getText());
					i.setFee(Double.parseDouble(this.treatmentFeeInput.getText()));
					TreatmentData.save(i);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Save could not be completed!");
			}
		});
		treatmentAddButton.setOnAction(e -> {
			Treatment i = new Treatment();
			treatmentData.add(i);
			treatmentTable.getSelectionModel().select(i);
		});
		treatmentDeleteButton.setOnAction(e -> {
			try {
				TreatmentData.delete(treatmentTable.getSelectionModel().getSelectedItem().getTreatmentId());
				Global.showNotification("Treatment deleted!", NotificationType.Success);
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

		branchSaveButton.setOnAction(e -> {
			try {
				Branch i = branchList.getSelectionModel().getSelectedItem();
				if (i != null) {
					i.setName(this.branchNameInput.getText());
					BranchData.save(i);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Save could not be completed!");
			}
		});
		branchAddButton.setOnAction(e -> {
			Branch i = new Branch();
			branchList.getItems().add(i);
			branchList.getSelectionModel().select(i);
		});
		branchDeleteButton.setOnAction(e -> {
			try {
				BranchData.delete(branchList.getSelectionModel().getSelectedItem().getBranchId());
				Global.showNotification("Branch deleted!", NotificationType.Success);

			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

		bloodGroupSaveButton.setOnAction(e -> {
			try {
				BloodGroup i = bloodGroupList.getSelectionModel().getSelectedItem();
				if (i != null) {
					i.setName(this.bloodGroupNameInput.getText());
					BloodGroupData.save(i);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Save could not be completed!");
			}
		});
		bloodGroupAddButton.setOnAction(e -> {
			BloodGroup i = new BloodGroup();
			bloodGroupList.getItems().add(i);
			bloodGroupList.getSelectionModel().select(i);
		});
		bloodGroupDeleteButton.setOnAction(e -> {
			try {
				BloodGroupData.delete(bloodGroupList.getSelectionModel().getSelectedItem().getBloodGroupId());
				Global.showNotification("Blood Group deleted!", NotificationType.Success);

			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

	}

	// Configure the table, Create columns and set data collection
	public void configTable() {
		String[][] colNames;

		colNames = new String[][] { { "Name", "name" }, { "Salary", "wage" } };
		for (String[] colName : colNames) {
			TableColumn<Designation, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			designationTable.getColumns().add(col);
		}
		designationTable.setItems(designationData);

		colNames = new String[][] { { "Test", "name" }, { "Fee", "fee" } };
		for (String[] colName : colNames) {
			TableColumn<Test, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			testTable.getColumns().add(col);
		}
		testTable.setItems(testData);

		colNames = new String[][] { { "Treatment", "name" }, { "Fee", "fee" } };
		for (String[] colName : colNames) {
			TableColumn<Treatment, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			treatmentTable.getColumns().add(col);
		}
		treatmentTable.setItems(treatmentData);

		branchList.getItems().clear();
		BranchData.getList().forEach(x -> branchList.getItems().add(x));

		bloodGroupList.getItems().clear();
		BloodGroupData.getList().forEach(x -> bloodGroupList.getItems().add(x));
	}

	// Load data to the tables
	public void loadDesignationData() {
		int sel = designationTable.getSelectionModel().getSelectedItem() == null ? -1
				: designationTable.getSelectionModel().getSelectedItem().getDesignationId();
		designationData.removeAll(designationData);
		DesignationData.getList().forEach(x -> designationData.add(x));

		if (sel > 0) {
			designationTable.getItems().forEach(x -> {
				if (sel == x.getDesignationId()) {
					designationTable.getSelectionModel().select(x);
					return;
				}
			});
		}
	}

	public void loadTestData() {
		int sel = testTable.getSelectionModel().getSelectedItem() == null ? -1
				: testTable.getSelectionModel().getSelectedItem().getTestId();
		testData.removeAll(testData);
		TestData.getList().forEach(x -> testData.add(x));

		if (sel > 0) {
			testTable.getItems().forEach(x -> {
				if (sel == x.getTestId()) {
					testTable.getSelectionModel().select(x);
					return;
				}
			});
		}
	}

	public void loadTreatmentData() {
		int sel = treatmentTable.getSelectionModel().getSelectedItem() == null ? -1
				: treatmentTable.getSelectionModel().getSelectedItem().getTreatmentId();
		treatmentData.removeAll(treatmentData);
		TreatmentData.getList().forEach(x -> treatmentData.add(x));

		if (sel > 0) {
			treatmentTable.getItems().forEach(x -> {
				if (sel == x.getTreatmentId()) {
					treatmentTable.getSelectionModel().select(x);
					return;
				}
			});
		}
	}

	public void loadBranchData() {
		branchList.getItems().clear();
		BranchData.getList().forEach(x -> branchList.getItems().add(x));
	}

	public void loadBloodGroupData() {
		bloodGroupList.getItems().clear();
		BloodGroupData.getList().forEach(x -> bloodGroupList.getItems().add(x));
	}
	


}
