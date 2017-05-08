package com.sumedhe.emedy.controller;



import com.sumedhe.emedy.data.WardData;
import com.sumedhe.emedy.model.Ward;
import com.sumedhe.emedy.service.DBException;

import javafx.event.ActionEvent;

public class MainController {
	
	public void testButton(ActionEvent event){

		
		try {
			WardData.add(new Ward("ward 01" , 200));
		} catch (DBException e1) {
			System.out.println("ssss" + e1.getMessage());
			e1.printStackTrace();
		}
	}

}
