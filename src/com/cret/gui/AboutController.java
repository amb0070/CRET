package com.cret.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * 
 * Controller for About window.
 * 
 * @author Adrian Marcos
 * @version 1.0
 */
public class AboutController {

	
	/**
	 * FXML element.
	 * 
	 * Button to close window
	 */
	@FXML
	private Button btnClose;
	
	/**
	 * FXML function.
	 * 
	 * Function to close window when button is pressed.
	 * 
	 * @param event Click event.
	 */
	@FXML
	private void closeWindow(ActionEvent event) {
	    Stage stage = (Stage) btnClose.getScene().getWindow();
	    stage.close();
	}
}
