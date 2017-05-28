package com.sumedhe.emedy.common;

import java.util.List;
import java.util.Optional;

import com.sumedhe.emedy.employee.Doctor;
import com.sumedhe.emedy.misc.BloodGroup;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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

}
