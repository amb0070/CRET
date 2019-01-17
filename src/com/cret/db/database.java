package com.cret.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cret.gui.GuiUtils;

import javafx.scene.control.Alert.AlertType;

public class Database {

	private String dbName;
	
	private String url = "jdbc:sqlite:";
	
	private Connection conn;
	
	private Statement stmt;
	
	private ResultSet rs;
	
	public Database(String dbName) {
		
		this.dbName = dbName;
		url = url + this.dbName;
		connect2();
	}
	
	public String getDatabaseName() {
		return dbName;
	}
	
	private void connect2() {
		
		System.out.println("Connecting to " + dbName);
		
		try {
			conn = DriverManager.getConnection(url);
			 stmt = conn.createStatement();
			 System.out.println("Connection success");
			 
			 
		} catch (SQLException e) {

			//System.out.println(e.getMessage());
			GuiUtils.generateAlert(AlertType.ERROR, "FATAL ERROR", "Error connecting to Database");
			
		}
		
	}
	
	//Disconnect from DB.
	public void disconnect() {
		
		try {
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Dissconnection OK");
		}
		
	}
	
	
	public Connection connect() throws SQLException {
		
		conn = DriverManager.getConnection(url);
		
		
		return conn;
	}
	
	
	public ResultSet query(String query) {
		
		try {
			rs = stmt.executeQuery(query);
			
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			GuiUtils.generateAlert(AlertType.ERROR, "Error in query", "Error executing query");
		}
		
		return null;
		
	}

	
	public void createTables(String dbName) {

		/*
		 * CREATE TABLE projects (
			name varchar(30) NOT NULL,
			PRIMARY KEY (name)
			);
		
			CREATE TABLE data (
			name varchar(20) NOT NULL,
			ID varchar(10) NOT NULL,
			byte varchar(20) NOT NULL,
			projectName varchar(30) NOT NULL,
			PRIMARY KEY (name, ID, byte, projectName),
			FOREIGN KEY (projectName) REFERENCES projects(name)
			);
			*/
		
		String table1 = "CREATE TABLE projects (\n"
				+ "name varchar(30) NOT NULL,\n"
				+ "PRIMARY KEY (name)\n"
				+ ");\r\n";
		
		String table2 = "CREATE TABLE data ("
				+ "name varchar(20) NOT NULL,"
				+ "ID varchar(10) NOT NULL,"
				+ "byte varchar(20) NOT NULL,"
				+ "projectName varchar(30) NOT NULL,"
				+ "PRIMARY KEY (name, ID, byte, projectName),"
				+ "FOREIGN KEY (projectName) REFERENCES projects(name)" + 
				");";
		
			try {
					//DATABASE CREATED OK

					stmt.execute(table1);
					stmt.execute(table2);
					GuiUtils.generateAlert(AlertType.INFORMATION, "Database created successful", "Database has been created!");
					
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				GuiUtils.generateAlert(AlertType.ERROR, "FATAL ERROR", "Error creating Database");
			}

	}
}
