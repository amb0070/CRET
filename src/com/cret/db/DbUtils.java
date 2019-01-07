package com.cret.db;

import com.cret.gui.GuiUtils;

import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbUtils {

	//Checks if database exists.
	public static boolean checkDatabaseExist(String dbName) {

		File f = new File(dbName);
		
		if (!f.exists()) {
			return false;
		}
		
		String url = "jdbc:sqlite:" + dbName;
		
		String table1 = "SELECT name FROM sqlite_master WHERE type='table' AND name='data'";
		
		String table2 = "SELECT name FROM sqlite_master WHERE type='table' AND name='projects'";
		
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
			GuiUtils.generateAlert(AlertType.ERROR, "FATAL ERROR", "Error checking DB");
			return false;
		}
		
	}
	
}
