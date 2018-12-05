package com.cret.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

	
	public database() {
		
	}
	
	private void connect() {
		
		
	}
	
	private void disconnect() {
		
	}
	
	private void query(String query) {
		
	}
	
	private void createDatabase(String filename) {
		
		String url = "jdbc:sqlite:" + filename;
		try(Connection con = DriverManager.getConnection(url)){
			
			if (con != null) {
				DatabaseMetaData meta = con.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("New database has been created");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
