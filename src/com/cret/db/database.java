package com.cret.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.cret.gui.GuiUtils;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class Database {

	private String dbName;

	private String url = "jdbc:sqlite:";

	private Connection conn;

	private Statement stmt;

	private ResultSet rs;

	/**
	 * Constructor.
	 * 
	 * @param dbName Database name.
	 */
	public Database(String dbName) {

		this.dbName = dbName;
		url = url + this.dbName;
		connect2();
	}

	/**
	 * Returns database name.
	 * @return Database name.
	 */
	public String getDatabaseName() {
		return dbName;
	}

	private void connect2() {
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error connecting to database");
		}
	}

	/**
	 * Disconnect from database.
	 */
	public void disconnect() {

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection connect() throws SQLException {

		conn = DriverManager.getConnection(url);

		return conn;
	}

	public ResultSet query(String query, String param) {

		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);
			rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error executing query");
		}
		return null;
	}
	
	public ResultSet query(String query) {

		try {
			Statement stmt  = conn.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error executing query");
		}
		return null;
	}
	
	public boolean delete(String query, String value) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, value);
		if (pstmt.executeUpdate()>=0) {
			return true;
		} else {
			return false;
		}
	}

	public void createTables(String dbName) {

		/**
		 * CREATE TABLE projects ( name varchar(30) NOT NULL, PRIMARY KEY (name) );
		 *
		 * CREATE TABLE data ( name varchar(20) NOT NULL, ID varchar(10) NOT NULL, byte
		 * varchar(20) NOT NULL, projectName varchar(30) NOT NULL, PRIMARY KEY (name,
		 * ID, byte, projectName), FOREIGN KEY (projectName) REFERENCES projects(name)
		 * );
		 */

		String tableProjects = "CREATE TABLE projects (\n" + "name varchar(30) NOT NULL,\n" + "PRIMARY KEY (name)\n"
				+ ");\r\n";

		String tableData = "CREATE TABLE data (" + "name varchar(20) NOT NULL," + "ID varchar(10) NOT NULL,"
				+ "byte varchar(20) NOT NULL," + "projectName varchar(30) NOT NULL,"
				+ "PRIMARY KEY (name, ID, byte, projectName)," + "FOREIGN KEY (projectName) REFERENCES projects(name)"
				+ ");";

		try {
			stmt.execute(tableProjects);
			stmt.execute(tableData);
			GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Database has been created!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Error creating database");
		}

	}
}
