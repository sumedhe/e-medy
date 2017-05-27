package com.sumedhe.emedy.common;

import java.util.List;

import com.sumedhe.emedy.employee.Doctor;
import com.sumedhe.emedy.misc.BloodGroup;

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
	

}
