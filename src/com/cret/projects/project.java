package com.cret.projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cret.gui.GuiUtils;

import javafx.scene.control.Alert.AlertType;

public class Project {
	
	public Project(String name) {
		
		setProjectName(name);
		
	}
	
	//PROJECT
		//Name
		//MAP
			//Data
			//ID
			//Byte
	
	//Project Name
	private String projectName;
	
	//Map with analyzed can data
	//Structure:
		//Data, ID, Byte
			//List 0 - ID
			//List 1 - Byte
	
	private Map<String, List<String>> analyzedValues = new HashMap<>();
	
	
	private Connection connect() {
		String url = "jdbc:sqlite:cret.db";
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	//DONE
	public void setProjectName(String name) {
		projectName = name;
	}
	
	//DONE
	public String getProjetName() {
		return this.projectName;
	}
	
	//DONE
	public Map<String, List<String>> getAllData(){
		return analyzedValues;
	}
	
	//TO TEST
	//Return map value 1 from key (ID)
	public String getMapValue1(String key) {
		
		if (analyzedValues.containsKey(key)) {
			
			List<String> values = analyzedValues.get(key);
			
			return values.get(0);
			
		} else {
			return "";
		}
	}
	
	//TO TEST
	//Return map value 2 from key (byte)
	public String getMapValue2(String key) {
		
		if (analyzedValues.containsKey(key)) {
			
			List<String> values = analyzedValues.get(key);
			
			return values.get(1);
			
		} else {
			return "";
		}
	}
	
	//TO TEST
	//Return all keys (identified names)
	public List<String> getMapKeys() {
		
		List<String> list = new ArrayList<String>(analyzedValues.keySet());
		
		return list;
	}
	
	//TO TEST
	public void setCompleteMap(Map<String, List<String>> newMap) {
		analyzedValues = newMap;
	}
	
	
	//TO TEST
	public void setMapKey(String key) {
		
		if (analyzedValues.containsKey(key)) {
			GuiUtils.generateAlert(AlertType.ERROR, "ERROR EN MAP", "key repetidas bro");
		} else {
			
			List<String> list = new ArrayList<String>();
			//Add empty list
			analyzedValues.put(key, list);
		}

		
	}
	
	//TO TEST
	public void setMapValues(String key, String id, String byteData) {
		
		if (analyzedValues.containsKey(key)) {
			
			List<String> list = new ArrayList<String>();
			list.add(id);
			list.add(byteData);
			
			analyzedValues.put(key, list);
			
		} else {
			
			System.out.println("No key on map");
		}
		
	}
	
	
	//Save project in database
	public void saveProject() {
		
		String sqlProject = "INSERT INTO projects VALUES(?)";
		String sqlData = "INSERT INTO data VALUES (?,?,?,?)";
		
	       try (Connection conn = this.connect();
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
	            System.out.println(e.getMessage());
	        }
	       
	       
		
		

		
		
		
		
		
	}

}
