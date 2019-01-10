package com.cret.gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cret.db.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ProjectListController {
	
	private Database db;

	@FXML
	Button OpenButton;
	
	@FXML
	Button CancelButton;
	
	@FXML
	Button btnDel;
	
	@FXML
	ListView list;
	
	
	
	@FXML
	private void closeWindow(ActionEvent event) {
		
	    Stage stage = (Stage) CancelButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void openProject(ActionEvent event) {
		
		String projectName = list.getSelectionModel().getSelectedItem().toString();
		
		
	}
	
	
	@FXML
	private void deleteProject(ActionEvent event) {
		
		String itemToDelete = list.getSelectionModel().getSelectedItem().toString();
		
		
		String sql = "DELETE FROM projects WHERE name = ?";
		
		GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Project deleted");
		
		
		//Delete item from list
		list.getItems().remove(list.getSelectionModel().getSelectedItem());
		
		
	}
	
	@FXML
	private void initialize() {
		
		db = new Database("cret.db");
		
		String query = "SELECT name FROM projects";
		
		ResultSet rs = db.query(query);
		
		try {
			while(rs.next()) {
				System.out.println(rs.getString("name"));
				list.getItems().add(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			GuiUtils.generateAlert(AlertType.ERROR, "Error getting data", "Error retrieveing data from database");
		}
		
		
	}

}
