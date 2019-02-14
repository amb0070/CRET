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
 * @version 1.0
 *
 */
public class ImportController {

	/**
	 * Database object.
	 */
	private Database db;

	/**
	 * FXML element.
	 * 
	 */
	@FXML
	private Button btnCancel;

	/**
	 * FXML element.
	 * 
	 */
	@FXML
	private Button btnImport;

	/**
	 * FXML element.
	 * 
	 */
	@FXML
	private ListView<String> listProjects;

	/**
	 * FXML Function.
	 * 
	 * Closes current window.
	 * 
	 * @param event Click event of the user.
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
			while (rs.next()) {
				listProjects.getItems().add(rs.getString("name"));
			}
		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error retrieveing data from database");
		}
	}

	/**
	 * FXML Function.
	 * 
	 * @param event Click event of the user.
	 * @throws IOException  Exception with file.
	 * @throws SQLException Exception on SQL query.
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
			}

		}

	}

	/**
	 * FXML Function.
	 * 
	 * Executed when window loads.
	 */
	@FXML
	private void initialize() {
		updateList();
	}

}
