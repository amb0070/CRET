package com.cret.db;

import com.cret.gui.GuiUtils;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
 * Database utilities.
 * 
 * Used to check if the database exists, and method to connect.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class DbUtils {

	
	/**
	 * Connects to database and returns the connection.
	 * 
	 * @return Connection to database.
	 */
	public static Connection connect() {

	        String url = "jdbc:sqlite:cret.db";
	        
	        Connection conn = null;
	        
	        try {
	            conn = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	        	GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error connecting to database.");
	        }
	        return conn;
	    }


	/**
	 * 
	 * Checks if the database exists and has correct structure.
	 * 
	 * @param dbName Database file name.
	 * @return True if database exists.
	 */
	public static boolean checkDatabaseExist(String dbName) {

		File f = new File(dbName);
		
		/**
		 * Check if file exists.
		 */
		if (!f.exists()) {
			return false;
		}
		
		String url = "jdbc:sqlite:" + dbName;
		
		String table1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='data'";
		
		String table2 = "SELECT name FROM sqlite_master WHERE type='table' AND name='projects'";
		
		/**
		 * Checks if it's possible to connect and checks the structure.
		 */
		
		try (Connection con = DriverManager.getConnection(url)){
			
			if (con != null) {

				
                Statement stmt = con.createStatement();
                
                ResultSet rs = stmt.executeQuery(table1);
                
                if (!rs.next()) {
                	return false;
                }
                while(rs.next()) {
                	System.out.println(rs.getString("name"));
                	if(!rs.getString("name").equals("data")) {
                		return false;
                	}
                }
                
                rs = stmt.executeQuery(table2);
                
                if (!rs.next()) {
                	return false;
                }
                
                while(rs.next()) {
                	System.out.println(rs.getString("name"));
                	if (!rs.getString("name").equals("projects")) {
                		return false;
                	}
                }
                
                return true;
                
			} else {
				
				return false;
				
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error checking database");
			return false;
		}
		
	}
	
}
