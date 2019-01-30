package com.cret.gui;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.cret.db.Database;
import com.cret.projects.json;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Adrian Marcos
 *
 */
public class ImportController {

	/**
	 * 
	 */
	private Database db;
	
	/**
	 * 
	 */
	@FXML
	private Button btnCancel;
	
	/**
	 * 
	 */
	@FXML
	private Button btnImport;
	
	/**
	 * 
	 */
	@FXML
	private ListView listProjects;
	
	/**
	 * @param event
	 */
	@FXML
	private void closeWindow(ActionEvent event) {
		
	    Stage stage = (Stage) btnCancel.getScene().getWindow();
	    stage.close();
	}
	
	/**
	 * 
	 */
	private void updateList() {
		
		db = new Database("cret.db");
		
		String query = "SELECT name FROM projects";
		
		ResultSet rs = db.query(query);
		listProjects.getItems().clear();
		try {
			while(rs.next()) {
				listProjects.getItems().add(rs.getString("name"));
			}
		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "Error getting data", "Error retrieveing data from database");
		}
	}
	
	/**
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void importProject(ActionEvent event) throws IOException, SQLException {
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import JSON file");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "*.json"));
		File selectedFile = fileChooser.showOpenDialog(btnCancel.getScene().getWindow());
		
		if (selectedFile != null) {


			try {
					String jsonFile = json.fileToString(selectedFile);
					if (json.isJSON(jsonFile)) {

						json.importJSONProject(jsonFile);
						updateList();
					} else {
						GuiUtils.generateAlert(AlertType.WARNING, "Not valid file", "Selected file is not a JSON file");
					}

			} catch (IOException e) {
				GuiUtils.generateAlert(AlertType.ERROR, "Not valid file", "Selected file is not a JSON file");
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 
	 */
	@FXML
	private void initialize() {
		
		updateList();

	}
	
}
