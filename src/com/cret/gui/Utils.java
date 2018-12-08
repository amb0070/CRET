package com.cret.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {

	
	public static void generateAlert(AlertType type, String title, String msg) {
		
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(msg);
		//alert.setContentText("I have a great message for you!");
		alert.showAndWait().ifPresent(rs -> {
		});
	}
}
