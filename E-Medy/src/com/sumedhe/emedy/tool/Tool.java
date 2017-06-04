package com.sumedhe.emedy.tool;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.sumedhe.emedy.model.BloodGroup;
import com.sumedhe.emedy.model.Doctor;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class Tool {
	public static BloodGroup getBloodGroupFrom(List<BloodGroup> bloodGroupList, int bloodGroupId){
		for (BloodGroup bg : bloodGroupList){
			if (bg.getBloodGroupId() == bloodGroupId){
				return bg;
			}
		}
		return null;
	}
	
	public static Doctor getDoctorFrom(List<Doctor> doctorList, int doctorId){
		for (Doctor d : doctorList){
			if (d.getDoctorId() == doctorId){
				return d;
			}
		}
		return null;
	}
	
	public static ButtonType showConfirmation(String message, String title){
		Alert a = new Alert(AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
		a.setTitle(title);
		a.setHeaderText(message);
		Optional<ButtonType> result = a.showAndWait();
		return result.get();
	}
	
	public static ButtonType showError(String message, String title){
		Alert a = new Alert(AlertType.ERROR, null, ButtonType.OK);
		a.setTitle(title);
		a.setHeaderText(message);
		Optional<ButtonType> result = a.showAndWait();
		return result.get();
	}
	
	public static Date DP2Date(DatePicker dp){
		if (dp.getValue() == null){
			return null;
		} else {
			return Date.valueOf(dp.getValue().toString());
		}
//		return Date.valueOf(dp.getValue() != null ? dp.getValue().toString() : "1900-01-01");
	}
	
	public static LocalDate Date2DP(Date date){
		if (date == null)
			return null;
		else
			return date.toLocalDate();
	}
	
	
//	public void setColumns(String[][] colNames, TableView<?> table){
//		for (String[] colName : colNames) {			
//			table.getColumns().add(new TableColumn<>(colName[0]));
//		}
//		for (int i = 0; i < table.getColumns().size(); i++){
//			TableColumn<?,?> col = table.getColumns().get(i);
//			col.setCellValueFactory(new PropertyValueFactory<?,?>(colNames[1]));
//			table.getColumns().get(i).setCellValueFactory(new PropertyValueFactory<?,?>(colNames[1]));
//		}
//	}
	
}
