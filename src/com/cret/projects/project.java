package com.cret.projects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class project {
	
	public project(String name) {
		
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
	
	private Map<String, List<String>> analyzedValues = new HashMap<>();
	
	
	public void setProjectName(String name) {
		projectName = name;
	}
	
	public String getProjetName() {
		return this.projectName;
	}
	
	public Map<String, List<String>> getAllData(){
		return analyzedValues;
	}
	
	//Return map value 1 from key (ID)
	public String getMapValue1(String key) {
		return analyzedValues.get
	}
	
	//Return map value 2 from key (byte)
	public String getMapValue2(String key) {
		return "";
	}
	
	//Return all keys (identified names)
	public String [] getMapKeys() {
		return new String[]{""};
	}
	
	public void setCompleteMap(Map<String, List<String>> newMap) {
		analyzedValues = newMap;
	}
	
	
	public void setMapKey(String key) {
		
	}
	
	public void setMapValues(String key, String id, String byteData) {
		
	}

}
