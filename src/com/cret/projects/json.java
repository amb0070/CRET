package com.cret.projects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import org.json.*;

public class json {
	
	public static String fileToString(File file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();
		
		return content;
	}

	
	public static boolean isTextFile(File f) throws IOException {
        String type = Files.probeContentType(f.toPath());
        if (type == null) {
            //type couldn't be determined, assume binary
            return false;
        } else if (type.startsWith("text")) {
            return true;
        } else {
            return false;
        }
    }
	
	public static boolean isJSON(String test) {
		
	    try {
	    	
	        new JSONObject(test);
	        
	    } catch (JSONException ex) {
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	public static void exportJSONProject(String name, Map<String, List<String>> values) {
		
		//Get data, generate json and open window dialog to store it.
		
	}
	
	public static void exportJSONProjects(String db) {
		//Export all database
	}
	
	public static void importJSONProjects(String file) {
		
		//Open window, select file, open, parse it and store in project options.

		
	}
	
}
