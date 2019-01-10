package com.cret.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.cret.gui.GuiUtils;

public class NewProjectController {

	public String newProjectName = "";
	
	@FXML
	private Button btnClose;
	
	@FXML
	private Button btnOk;
	
	@FXML
	private TextField txtProjectName;
	
	
	//DONE
	@FXML
	private void closeWindow(ActionEvent event) throws Exception{
		Platform.exit();
	}
	
	//DONE
	@FXML
	private void createProject(ActionEvent event) throws Exception{
		
		String projectName = txtProjectName.getText().trim();
		
		
		//Empty project
		if (projectName.equals("")) {
			
			GuiUtils.generateAlert(AlertType.INFORMATION, "Invalid project name", "An empty text is not a valid project name");
		} else {
			
			RootViewController.returnNewName(projectName);
			Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
		}
		
	}
	
	
}
