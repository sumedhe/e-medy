package com.sumedhe.emedy.controller;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;

import com.sumedhe.emedy.common.CacheEventListener;
import com.sumedhe.emedy.common.ComboBoxFilterListener;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.common.Tabular;
import com.sumedhe.emedy.config.Global;
import com.sumedhe.emedy.data.AdmissionTestData;
import com.sumedhe.emedy.data.AdmissionTreatmentData;
import com.sumedhe.emedy.data.TestData;
import com.sumedhe.emedy.data.TreatmentData;
import com.sumedhe.emedy.model.Admission;
import com.sumedhe.emedy.model.AdmissionTest;
import com.sumedhe.emedy.model.AdmissionTreatment;
import com.sumedhe.emedy.model.Test;
import com.sumedhe.emedy.model.Treatment;
import com.sumedhe.emedy.service.DBException;
import com.sumedhe.emedy.tool.Tool;
import com.sumedhe.emedy.tool.Validator;
import com.sumedhe.emedy.tool.ValidatorEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AdmissionTreatmentController extends AnchorPane implements Tabular {

	@FXML
	TableView<AdmissionTreatment> admissionTreatmentTable;

	@FXML
	TableView<AdmissionTest> admissionTestTable;

	@FXML
	TextArea treatmentNoteInput, testResultInput;

	@FXML
	ComboBox<Treatment> treatmentInput;

	@FXML
	ComboBox<Test> testInput;

	@FXML
	Button backButton, admissionTreatmentSaveButton, admissionTreatmentAddButton, admissionTreatmentDeleteButton,
			admissionTestSaveButton, admissionTestAddButton, admissionTestDeleteButton;

	@FXML
	TitledPane treatmentPane, testPane;

	@FXML
	Label paymentLabel;

	Node prev;
	Admission admission;
	Validator treatmentValidator = new Validator();
	Validator testValidator = new Validator();

	ObservableList<AdmissionTreatment> admissionTreatmentData = FXCollections.observableArrayList();
	ObservableList<AdmissionTest> admissionTestData = FXCollections.observableArrayList();
	double treatmentPayment;
	double testPayment;

	// Constructor
	public AdmissionTreatmentController(Admission admission, Node prev) {
		this.prev = prev;
		this.admission = admission;

		String url = "/com/sumedhe/emedy/view/AdmissionTreatmentView.fxml";
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
		treatmentInput.getItems().addAll(TreatmentData.getList());
		testInput.getItems().addAll(TestData.getList());
		new ComboBoxFilterListener<Treatment>(treatmentInput);
		new ComboBoxFilterListener<Test>(testInput);

		setAdmission(admission);
		setHandlers();
		configTable();
		loadData();
	}

	// Set handlers for the the UI components
	@Override
	public void setHandlers() {
		AdmissionTreatmentData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadTreatmentData();
			}
		});
		AdmissionTestData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				loadTestData();
			}
		});
		TreatmentData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				treatmentInput.getItems().clear();
				treatmentInput.getItems().addAll(TreatmentData.getList());
			}
		});
		TestData.getCache().addCacheEventListener(new CacheEventListener() {
			@Override
			public void updated(EventObject e) {
				testInput.getItems().clear();
				testInput.getItems().addAll(TestData.getList());
			}
		});
		backButton.setOnAction(e -> {
			Global.getHome().setWorkPanel(this.prev);
		});

		// Treatment section //
		admissionTreatmentTable.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						treatmentInput.setValue((newSelection.getTreatment()));
						treatmentNoteInput.setText(newSelection.getNote());
					}
				});
		admissionTreatmentSaveButton.setOnAction(e -> {
			if (treatmentValidator.checkValidity(new ValidatorEvent(this))) {
				try {
					AdmissionTreatment i = admissionTreatmentTable.getSelectionModel().getSelectedItem();
					if (i != null) {
						Global.log("a");
						i.setAdmissionId(this.admission.getAdmissionId());
						i.setTreatmentId(this.treatmentInput.getValue().getTreatmentId());
						i.setNote(this.treatmentNoteInput.getText());
						AdmissionTreatmentData.save(i);
					}
				} catch (Exception e1) {
					Global.logError(e1.getMessage(), "Save could not be completed!");
				}
			}
		});
		admissionTreatmentAddButton.setOnAction(e -> {
			AdmissionTreatment i = new AdmissionTreatment();
			admissionTreatmentData.add(i);
			admissionTreatmentTable.getSelectionModel().select(i);
		});
		admissionTreatmentDeleteButton.setOnAction(e -> {
			try {
				if (Tool.showConfirmation("Do you want to delete the treatment?", "Delete") == ButtonType.YES) {
					AdmissionTreatmentData.delete(
							admissionTreatmentTable.getSelectionModel().getSelectedItem().getAdmissionTreatmentId());
					Global.showNotification("Treatment deleted!", NotificationType.Success);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

		// Test Section //
		admissionTestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				testInput.setValue((newSelection.getTest()));
				testResultInput.setText(newSelection.getResult());
			}
		});
		admissionTestSaveButton.setOnAction(e -> {
			if (testValidator.checkValidity(new ValidatorEvent(this))) {
				try {
					AdmissionTest i = admissionTestTable.getSelectionModel().getSelectedItem();
					if (i != null) {
						Global.log("a");
						i.setAdmissionId(this.admission.getAdmissionId());
						i.setTestId(this.testInput.getValue().getTestId());
						i.setResult(this.testResultInput.getText());
						AdmissionTestData.save(i);
					}
				} catch (Exception e1) {
					Global.logError(e1.getMessage(), "Save could not be completed!");
				}
			}
		});
		admissionTestAddButton.setOnAction(e -> {
			AdmissionTest i = new AdmissionTest();
			admissionTestData.add(i);
			admissionTestTable.getSelectionModel().select(i);
		});
		admissionTestDeleteButton.setOnAction(e -> {
			try {
				if (Tool.showConfirmation("Do you want to delete the test?", "Delete") == ButtonType.YES) {
					AdmissionTestData
							.delete(admissionTestTable.getSelectionModel().getSelectedItem().getAdmissionTestId());
					Global.showNotification("Test deleted!", NotificationType.Success);
				}
			} catch (DBException e1) {
				Global.logError(e1.getMessage(), "Delete could not be completed! Alrady used by another field!");
			}
		});

		// Other handlers //
		treatmentPane.textProperty().addListener(e -> {
			showPayment();
		});
		testPane.textProperty().addListener(e -> {
			showPayment();
		});

		// Set handlers for Validation Checking
		treatmentValidator.addToCheckNull(treatmentInput);
		treatmentValidator.addToCheckEmpty(treatmentNoteInput);
		testValidator.addToCheckNull(testInput);
		testValidator.addToCheckEmpty(testResultInput);
	}

	@Override
	public void configTable() {
		String[][] colNames;

		// Treatment Table //
		colNames = new String[][] { { "Name", "treatmentName" }, { "Note", "note" }, { "Fee (Rs)", "fee" } };
		for (String[] colName : colNames) {
			TableColumn<AdmissionTreatment, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			admissionTreatmentTable.getColumns().add(col);
		}
		admissionTreatmentTable.setItems(admissionTreatmentData);

		// Test Table //
		colNames = new String[][] { { "Name", "testName" }, { "Result", "result" }, { "Fee (Rs)", "fee" } };
		for (String[] colName : colNames) {
			TableColumn<AdmissionTest, String> col = new TableColumn<>(colName[0]);
			col.setCellValueFactory(new PropertyValueFactory<>(colName[1]));
			admissionTestTable.getColumns().add(col);
		}
		admissionTestTable.setItems(admissionTestData);
	}

	@Override
	public void loadData() {
		loadTreatmentData();
		loadTestData();
	}

	public void loadTreatmentData() {
		int sel = getSelectedAdmissionTreatment() == null ? -1
				: getSelectedAdmissionTreatment().getAdmissionTreatmentId();
		try {
			treatmentPayment = 0;
			List<AdmissionTreatment> list = AdmissionTreatmentData.getByAdmissionId(this.admission.getAdmissionId());
			admissionTreatmentData.removeAll(admissionTreatmentData);
			for (AdmissionTreatment p : list) {
				admissionTreatmentData.add(p);
				treatmentPayment += p.getFee();
			}
			treatmentPane.setText("Treatment  -  Rs." + Double.toString(treatmentPayment));
		} catch (Exception e) {
			Global.log(e.getMessage());
		}

		if (sel > 0) {
			setSelectedAdmissionTreatment(sel);
		}
	}

	public void loadTestData() {
		int sel = getSelectedAdmissionTest() == null ? -1 : getSelectedAdmissionTest().getAdmissionTestId();
		try {
			testPayment = 0;
			List<AdmissionTest> list = AdmissionTestData.getByAdmissionId(this.admission.getAdmissionId());
			admissionTestData.removeAll(admissionTestData);
			for (AdmissionTest p : list) {
				admissionTestData.add(p);
				testPayment += p.getFee();
			}
			testPane.setText("Test  -  Rs." + Double.toString(testPayment));
		} catch (Exception e) {
			Global.log(e.getMessage());
		}

		if (sel > 0) {
			setSelectedAdmissionTest(sel);
		}
	}

	public void setAdmission(Admission admission) {
		this.admission = admission;
		loadData();
	}

	public void showPayment() {
		paymentLabel.setText("Payment: " + Double.toString(treatmentPayment + testPayment));
	}

	// Get the selected object of the table
	public AdmissionTreatment getSelectedAdmissionTreatment() {
		return admissionTreatmentTable.getSelectionModel().getSelectedItem();
	}

	public AdmissionTest getSelectedAdmissionTest() {
		return admissionTestTable.getSelectionModel().getSelectedItem();
	}

	// Select a row by it's ID
	public void setSelectedAdmissionTreatment(int admissionTreatmentId) {
		admissionTreatmentTable.getItems().forEach(p -> {
			if (p.getAdmissionTreatmentId() == admissionTreatmentId) {
				admissionTreatmentTable.getSelectionModel().select(p);
			}
		});
	}

	public void setSelectedAdmissionTest(int admissionTestId) {
		admissionTestTable.getItems().forEach(p -> {
			if (p.getAdmissionTestId() == admissionTestId) {
				admissionTestTable.getSelectionModel().select(p);
			}
		});
	}
}
