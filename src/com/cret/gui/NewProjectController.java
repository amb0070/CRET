package com.cret.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewProjectController {

	
	@FXML
	private Button btnClose;
	
	@FXML
	private Button btnOk;
	
	@FXML
	private TextField txtProjectName;
	
	@FXML
	private void closeWindow(ActionEvent event) throws Exception{
	    Stage stage = (Stage) btnClose.getScene().getWindow();
	    stage.close();
	}
	
	
}
