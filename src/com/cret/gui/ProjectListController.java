package com.cret.gui;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import com.cret.db.Database;
import com.cret.projects.Project;
import com.cret.staticdata.structures;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

/**
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class ProjectListController {

	/**
	 * Database object.
	 */
	private Database db;

	/**
	 * FXML Element
	 * 
	 * Button to open project.
	 */
	@FXML
	private Button btnOpenProject;

	/**
	 * FXML Element
	 * 
	 * Button to close window.
	 */
	@FXML
	private Button btnCancel;

	/**
	 * FXML Element
	 * 
	 * Button to delete project.
	 */
	@FXML
	private Button btnDeleteProject;

	/**
	 * FXML Element
	 * 
	 * ListView with projects.
	 */
	@FXML
	private ListView<String> listProjects;

	/**
	 * FXML Element
	 * 
	 * Button to import projects.
	 */
	@FXML
	private MenuItem btnImport;

	/**
	 * FXML Element
	 * 
	 * Button to export projects.
	 */
	@FXML
	private MenuItem btnExport;

	/**
	 * FXML Function
	 * 
	 * Closes window.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	private void closeWindow(ActionEvent event) {

		db.disconnect();
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	/**
	 * FXML Function
	 * 
	 * Opens selected project.
	 * 
	 * @param event Click event of the user.
	 * @throws SQLException
	 */
	@FXML
	private void openProject(ActionEvent event) throws SQLException {

		/**
		 * Reset all structures.
		 */
		structures.resetDashboardStructures1();
		structures.resetAnalisysStructures1();
		structures.resetRAWStructures();

		structures.valuesInDashboard1 = new HashMap<>();
		// Get selected project.
		String projectName = listProjects.getSelectionModel().getSelectedItem().toString();

		/**
		 * Set mode to dashboard
		 */
		structures.modeInterface1 = "Dashboard";

		structures.valuesInDashboard1 = new HashMap<>();

		String sqlQuery = "SELECT name, ID, byte FROM data WHERE projectName = ?";

		ResultSet rs = db.query(sqlQuery, projectName);

		while (rs.next()) {

			String dataName = rs.getString("name");
			String id = rs.getString("id");
			String byteNumber = rs.getString("byte");

			if (!structures.valuesInDashboard1.containsKey(dataName)) {
				structures.valuesInDashboard1.put(dataName, new ArrayList<>());
			}

			structures.valuesInDashboard1.get(dataName).add(id);
			structures.valuesInDashboard1.get(dataName).add(byteNumber);
			structures.dataQ1Dash1 = new HashMap<>();

		}

		// Sets new project
		RootViewController.projectObj = new Project(projectName);
		RootViewController.projectName.setValue(projectName);

		// Closes window
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();

	}

	/**
	 * FXML Function
	 * 
	 * Delete selected project.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	private void deleteProject(ActionEvent event) {

		if (listProjects.getSelectionModel().getSelectedItem() == null) {
			GuiUtils.generateAlert(AlertType.WARNING, "Select a item", "You must select a project to be deleted.");
		} else {

			// Ask for confirmation.
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete project");
			alert.setHeaderText("Are you sure you want to delete the project?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent()) {

				if (result.get() == ButtonType.OK) {

					/**
					 * Delete project from database.
					 */
					String itemToDelete = listProjects.getSelectionModel().getSelectedItem().toString();

					String sql = "DELETE FROM projects WHERE name = ?";
					String sql2 = "DELETE FROM data WHERE projectName = ?";

					try {
						if (!db.delete(sql, itemToDelete)) {
							GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error deleting project.");
						}
					} catch (SQLException e1) {
						GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error executing query");
					}

					try {
						if (!db.delete(sql2, itemToDelete)) {
							GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error deleting project.");
						}
					} catch (SQLException e) {
						GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error executing query");
					}

					// Delete item from list
					listProjects.getItems().remove(listProjects.getSelectionModel().getSelectedItem());

					GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Project deleted");
				}
			}
		}
	}

	/**
	 * FXML Function.
	 * 
	 * Opens window to import projects.
	 * 
	 * @param event Click event of the user.
	 * @throws IOException Error opening FXML document.
	 */
	@FXML
	private void importProject(ActionEvent event) throws IOException {
		// Load FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ImportView.fxml"));
		Parent importDialog = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		// Set icon.
		stage.getIcons().add(new Image("file:resources/icon.png"));
		// Set title.
		stage.setTitle("CRET - Import project");
		stage.setScene(new Scene(importDialog));
		stage.setResizable(false);
		// Show window.
		stage.show();

	}

	/**
	 * FXML funciton.
	 * 
	 * Opens window to export projects.
	 * 
	 * @param event
	 * @throws IOException Error opening FXML document.
	 */
	@FXML
	private void exportProject(ActionEvent event) throws IOException {

		// Load FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ExportView.fxml"));
		Parent exportDialog = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		// Set icon.
		stage.getIcons().add(new Image("file:resources/icon.png"));
		// Set title.
		stage.setTitle("CRET - Export project");
		stage.setScene(new Scene(exportDialog));
		stage.setResizable(false);
		// Show window.
		stage.show();

	}

	/**
	 * FXML function.
	 * 
	 * Executed when this window loads.
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

			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error retrieveing data from database");
		}

	}

}
