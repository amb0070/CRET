package com.cret.projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cret.db.DbUtils;
import com.cret.gui.GuiUtils;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * Class to manage projects.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class Project {

	/**
	 * Constructror. Sets the project name.
	 * @param name Project name.
	 */
	public Project(String name) {
		setProjectName(name);
	}


	private String projectName;

	/**
	 * Map to store identified values.
	 */
	private Map<String, List<String>> analyzedValues = new HashMap<>();

	
	/**
	 * Sets the project name.
	 * @param name New project name.
	 */
	public void setProjectName(String name) {
		projectName = name;
	}

	
	/**
	 * Returns the project name.
	 * @return String String with project name.
	 */
	public String getProjetName() {
		return this.projectName;
	}

	
	/**
	 * Retruns all analyzed values.
	 * 
	 * @return Map Map with analyzed values.
	 */
	public Map<String, List<String>> getAllData() {
		return analyzedValues;
	}

	/**
	 * Returns keys of the map.
	 * @return List List with keys of the map.
	 */
	public List<String> getMapKeys() {

		List<String> list = new ArrayList<String>(analyzedValues.keySet());

		return list;
	}

	
	/**
	 * Sets the map.
	 * @param newMap New map to set.
	 */
	public void setCompleteMap(Map<String, List<String>> newMap) {
		analyzedValues = newMap;
	}

	
	/**
	 * Creates a new key in the map.
	 * @param key String with the new key.
	 */
	public void setMapKey(String key) {

		if (analyzedValues.containsKey(key)) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Duplicated value");
		} else {

			List<String> list = new ArrayList<String>();
			// Add empty list
			analyzedValues.put(key, list);
		}

	}

	
	/**
	 * Stores project in database.
	 */
	public void saveProject() {

		String sqlProject = "INSERT INTO projects VALUES(?)";
		String sqlData = "INSERT INTO data VALUES (?,?,?,?)";
		
		try (Connection conn = DbUtils.connect();
			
			PreparedStatement pstmt = conn.prepareStatement(sqlProject)) {
			pstmt.setString(1, this.projectName);
			pstmt.executeUpdate();
			PreparedStatement pstmt2 = conn.prepareStatement(sqlData);
			pstmt2 = conn.prepareStatement(sqlData);

			for (String key : analyzedValues.keySet()) {
				String id = analyzedValues.get(key).get(0);
				String byteNumber = analyzedValues.get(key).get(1);

				pstmt2.setString(1, key);
				pstmt2.setString(2, id);
				pstmt2.setString(3, byteNumber);
				pstmt2.setString(4, this.projectName);
				pstmt2.executeUpdate();
				
			}

		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Internal SQL error.");
		}
	}
}
