package com.cret.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Adrian Marcos
 *
 */
public class NewProjectController {
	
	/**
	 * FXML element.
	 * Button to cancel.
	 */
	@FXML
	private Button btnCancel;
	
	/**
	 * FXML element.
	 * Button to save project.
	 */
	@FXML
	private Button btnSaveProjectName;
	
	/**
	 * FXML element.
	 * TextField to enter the project name.
	 */
	@FXML
	private TextField txtProjectName;
	
	/**
	 * FXML function.
	 * Closes the current window.
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void closeWindow(ActionEvent event) throws Exception{
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * FXML function.
	 * Checks the project name, and if it's valid, returns the project name and closes window.
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void createProject(ActionEvent event) throws Exception{
		
		String projectName = txtProjectName.getText().trim();
		
		//Check if project name is not empty.
		if (projectName.equals("")) {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Invalid project name", "An empty text is not a valid project name");
		} else {
			RootViewController.returnNewName(projectName);
			Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
		}	
	}	
}
