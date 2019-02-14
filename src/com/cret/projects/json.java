package com.cret.projects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cret.db.Database;
import com.cret.db.DbUtils;
import com.cret.gui.GuiUtils;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * Class to generate JSON files and parse it.
 * 
 * Imports and exports files into internal database.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class json {

	/**
	 * Read file and makes it a String.
	 * 
	 * @param file JSON file.
	 * @return Content with file content.
	 * @throws IOException File not found.
	 */
	public static String fileToString(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();

		return content;
	}

	/**
	 * Checks if file is a JSON.
	 * 
	 * @param fileToCheck File to check.
	 * @return True if it is JSON content.
	 */
	public static boolean isJSON(String fileToCheck) {

		try {

			new JSONObject(fileToCheck);

		} catch (JSONException ex) {
			try {
				new JSONArray(fileToCheck);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Exports a selected project into JSON.
	 * 
	 * @param projectName Project name to export.
	 * @return JSONObject JSONObject with the content.
	 * @throws IOException
	 * @throws JSONException
	 * @throws SQLException
	 */
	public static JSONObject exportJSONProject(String projectName) throws IOException, JSONException, SQLException {

		JSONObject completeJson = new JSONObject();

		JSONObject elementJson = null;

		Database db = new Database("cret.db");

		String sql = "SELECT name, ID, byte FROM data WHERE projectName = ?";

		ResultSet rs = db.query(sql, projectName);

		while (rs.next()) {

			elementJson = new JSONObject();

			String dataName = rs.getString("name");
			String id = rs.getString("ID");
			String byteNumber = rs.getString("byte");

			elementJson.put("Data", dataName).put("ID", id).put("Byte", byteNumber);

			completeJson.append("values", elementJson);

		}
		completeJson.put("projectName", projectName);
		db.disconnect();
		return completeJson;

	}

	/**
	 * Imports JSON file to database.
	 * 
	 * @param strFile JSON file.
	 * @throws SQLException Query error.
	 */
	public static void importJSONProject(String strFile) throws SQLException {

		JSONObject jsonFile = new JSONObject(strFile);

		String sql = "INSERT INTO projects VALUES (?)";
		String sql2 = "INSERT INTO data VALUES (?,?,?,?)";

		String querySelect = "SELECT name FROM projects WHERE name = ?";

		boolean duplicated = false;

		if (jsonFile.has("projectName")) {
			String projectName = jsonFile.getString("projectName");

			try (Connection conn = DbUtils.connect();

					PreparedStatement pstmtQuery = conn.prepareStatement(querySelect)) {
				pstmtQuery.setString(1, projectName);
				ResultSet rs = pstmtQuery.executeQuery();
				if (rs.next()) {
					duplicated = true;
					GuiUtils.generateAlert(AlertType.INFORMATION, "Duplicated project.",
							"Project name already exists. Please, select another name.");
				}
			}

			if (!duplicated) {
				try (Connection conn = DbUtils.connect();

						PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, projectName);
					pstmt.executeUpdate();

					JSONArray array = jsonFile.getJSONArray("values");
					for (int i = 0; i < array.length(); i++) {
						String id = array.getJSONObject(i).getString("ID");
						String name = array.getJSONObject(i).getString("Data");
						String byteNumber = array.getJSONObject(i).getString("Byte");

						PreparedStatement pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, name);
						pstmt2.setString(2, id);
						pstmt2.setString(3, byteNumber);
						pstmt2.setString(4, projectName);
						pstmt2.executeUpdate();

					}

					GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Project has been imported.");

				}

			}

		} else {
			GuiUtils.generateAlert(AlertType.INFORMATION, "Error", "Not valid JSON file");
		}

	}

}
