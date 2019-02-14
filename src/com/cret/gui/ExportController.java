package com.cret.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;
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
public class ExportController {

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
	private Button btnExport;

	/**
	 * 
	 */
	@FXML
	private ListView<String> listProjects;

	/**
	 * @param event
	 */
	@FXML
	private void closeWindow(ActionEvent event) {

		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	/**
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 * @throws JSONException
	 */
	@FXML
	private void exportProject(ActionEvent event) throws IOException, JSONException, SQLException {
		String projectName;

		if (listProjects.getSelectionModel().getSelectedItem() != null) {
			projectName = listProjects.getSelectionModel().getSelectedItem().toString();
			JSONObject jsonToWrite = json.exportJSONProject(projectName);

			FileChooser fileChooser = new FileChooser();

			// Set extension filter for text files
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			File file = fileChooser.showSaveDialog(btnCancel.getParent().getScene().getWindow());

			if (file != null) {

				saveTextToFile(jsonToWrite.toString(4), file);
				GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Project exported!");
			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "No project selected",
					"You must select a project to export.");
		}
	}

	private void saveTextToFile(String json, File file) throws FileNotFoundException {
		PrintWriter writer;
		writer = new PrintWriter(file);
		writer.println(json);
		writer.close();
	}

	/**
	 * 
	 */
	@FXML
	private void initialize() {

		db = new Database("cret.db");

		String query = "SELECT name FROM projects";

		ResultSet rs = db.query(query);

		try {
			while (rs.next()) {
				listProjects.getItems().add(rs.getString("name"));
			}
		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error retrieveing data from database.");
		}

	}

}
