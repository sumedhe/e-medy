package com.sumedhe.emedy.tool;

import java.util.EventObject;
import java.util.regex.Pattern;

import javax.swing.event.EventListenerList;

import com.mysql.jdbc.StringUtils;
import com.sumedhe.emedy.common.NotificationType;
import com.sumedhe.emedy.config.Global;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Validator {
	boolean isValid;
	EventListenerList listenerList = new EventListenerList();
	Pattern phonePattern = Pattern.compile("[^0-9+ -]");
	Pattern nicPattern = Pattern.compile("[^0-9V]");

	public Validator() {
		
	}

	// Handler
	public void addValidatorEventListener(ValidatorEventListener l) {
		listenerList.add(ValidatorEventListener.class, l);
	}

	public void removeValidatorEventListener(ValidatorEventListener l) {
		listenerList.remove(ValidatorEventListener.class, l);
	}
	
	public boolean checkValidity(ValidatorEvent e){
		isValid = true;
		ValidatorEventListener[] ls = listenerList.getListeners(ValidatorEventListener.class);
		for (ValidatorEventListener l : ls){
			l.check(e);
		}
		if (!isValid){
			Global.showNotification("Can't Save! Please check the fields...", NotificationType.Information);
		}
		return isValid;
	}
	
	
	
	// Check for errors
	void checkForEmpty(TextField t) {
		setError(t, t.getText() == null || t.getText().equals(""));
	}
	
	void checkForEmpty(TextArea t){
		setError(t, t.getText().equals(""));
	}
	
	void checkForNumeric(TextField t){
		setError(t, !StringUtils.isStrictlyNumeric(t.getText()));
	}
	
	void checkForPhone(TextField t){
		setError(t, phonePattern.matcher(t.getText()).find());
	}
	
	void checkForNic(TextField t){
		setError(t, nicPattern.matcher(t.getText()).find() || !t.getText().contains("V"));
	}
	
	void checkForExistingId(TextField t){
		
	}
	
	<T> void checkForNull(ComboBox<T> c) {
		setError(c, c.getValue() == null);
	}
	
	void checkForNull(DatePicker d){
		setError(d, d.getValue() == null);
	}

	public void setError(Node component, boolean isError) {
		if (isError) {
			component.getStyleClass().add("input-error");
			isValid = false;
		} else {
			component.getStyleClass().remove("input-error");
		}
	}
	
	
	
	// Join objects and handlers
	public void addToCheckEmpty(TextField t) {
		t.focusedProperty().addListener(e -> {
			if (!t.isFocused()) {
				checkForEmpty(t);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForEmpty(t);
			}
		});
	}
	
	public void addToCheckEmpty(TextArea t) {
		t.focusedProperty().addListener(e -> {
			if (!t.isFocused()) {
				checkForEmpty(t);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForEmpty(t);
			}
		});
	}
	
	public void addToCheckNumeric(TextField t) {
		t.focusedProperty().addListener(e -> {
			if (!t.isFocused()) {
				checkForNumeric(t);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForNumeric(t);
			}
		});
	}
	
	public void addToCheckPhone(TextField t){
		t.focusedProperty().addListener(e -> {
			if (!t.isFocused()) {
				checkForPhone(t);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForPhone(t);
			}
		});
	}
	
	public void addToCheckNic(TextField t){
		t.focusedProperty().addListener(e -> {
			if (!t.isFocused()) {
				checkForNic(t);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForNic(t);
			}
		});
	}
	
	
	public <T> void addToCheckNull(ComboBox<T> c) {
		c.focusedProperty().addListener(e -> {
			if (!c.isFocused()) {
				checkForNull(c);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForNull(c);
			}
		});
	}
	
	public <T> void addToCheckNull(DatePicker d) {
		d.focusedProperty().addListener(e -> {
			if (!d.isFocused()) {
				checkForNull(d);
			}
		});
		this.addValidatorEventListener(new ValidatorEventListener() {
			@Override
			public void check(EventObject e) {
				checkForNull(d);
			}
		});
	}

}
