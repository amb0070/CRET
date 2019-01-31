package com.cret.gui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import com.cret.db.Database;
import com.cret.db.DbUtils;
import com.cret.projects.Project;
import com.cret.staticData.structures;
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
		
	    Stage stage = (Stage) btnCancel.getScene().getWindow();
	    stage.close();
	}
	
	/**
	 * FXML Function
	 * 
	 * Opens selected project.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	private void openProject(ActionEvent event) {
		
		/**
		 * Reset all structures.
		 */
		structures.resetDashboardStructures();
		structures.resetAnalisysStructures();
		structures.resetRAWStructures();
		
		//Get selected project.
		String projectName = listProjects.getSelectionModel().getSelectedItem().toString();
		
		/**
		 * Set mode to dashboard
		 */
		structures.mode = "Dashboard";
		
		structures.valuesInDashboard = new HashMap<>();
		
        
        String sqlQuery = "SELECT name, ID, byte FROM data WHERE projectName = ?";
        
        try (Connection conn = DbUtils.connect();

        	 PreparedStatement pstmt  = conn.prepareStatement(sqlQuery)){
        	            pstmt.setString(1,projectName);
        	            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
            	
            	String dataName = rs.getString("name");
            	String id = rs.getString("id");
            	String byteNumber = rs.getString("byte");
                
                if (!structures.valuesInDashboard.containsKey(dataName)) {
                	structures.valuesInDashboard.put(dataName, new ArrayList<>());
                }
                
                structures.valuesInDashboard.get(dataName).add(id);
                structures.valuesInDashboard.get(dataName).add(byteNumber);
                structures.dataQ1Dash = new HashMap<>();
                
            }
            
            //Sets new project
            RootViewController.projectObj = new Project(projectName);
            RootViewController.projectName.setValue(projectName);
            
            //Closes window
    	    Stage stage = (Stage) btnCancel.getScene().getWindow();
    	    stage.close();
    	    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
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
			GuiUtils.generateAlert(AlertType.WARNING, "Select a item", "You must select a project to delete");
		} else {
			
			//Ask for confirmation.
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete project");
			alert.setHeaderText("Are you sure you want to delete the project?");
	
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == ButtonType.OK){
				
				/**
				 * Delete project from database.
				 */
				String itemToDelete = listProjects.getSelectionModel().getSelectedItem().toString();
	
				String sql = "DELETE FROM projects WHERE name = ?";
				String sql2 = "DELETE FROM data WHERE projectName = ?";
				 try (Connection conn = DbUtils.connect();
			            PreparedStatement pstmt = conn.prepareStatement(sql)) {
					 
			            pstmt.setString(1, itemToDelete);
			            
			            pstmt.executeUpdate();
			 
			        } catch (SQLException e) {
			        	GuiUtils.generateAlert(AlertType.ERROR, "Internal error", "Error deleting project.");
			        }
				 
				 try (Connection conn = DbUtils.connect();
				            PreparedStatement pstmt = conn.prepareStatement(sql2)) {

				            pstmt.setString(1, itemToDelete);
				            
				            pstmt.executeUpdate();
				 
				        } catch (SQLException e) {
				            GuiUtils.generateAlert(AlertType.ERROR, "Internal error", "Error deleting project.");
				        }
				
				//Delete item from list
				listProjects.getItems().remove(listProjects.getSelectionModel().getSelectedItem());
				
				GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Project deleted");
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
		//Load FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ImportView.fxml"));
		Parent importDialog = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		//Set icon.
		stage.getIcons().add(new Image("file:resources/icon.png"));
		//Set title.
		stage.setTitle("CRET - Import project");
		stage.setScene(new Scene(importDialog));
		stage.setResizable(false);
		//Show window.
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
		
		//Load FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cret/gui/ExportView.fxml"));
		Parent exportDialog = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		//Set icon.
		stage.getIcons().add(new Image("file:resources/icon.png"));
		//Set title.
		stage.setTitle("CRET - Export project");
		stage.setScene(new Scene(exportDialog));
		stage.setResizable(false);
		//Show window.
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
			while(rs.next()) {
				listProjects.getItems().add(rs.getString("name"));
			}
		} catch (SQLException e) {

			GuiUtils.generateAlert(AlertType.ERROR, "Error getting data", "Error retrieveing data from database");
		}

	}

}
