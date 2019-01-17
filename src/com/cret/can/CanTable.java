package com.cret.can;

import javafx.beans.property.SimpleStringProperty;

public class CanTable {

	private SimpleStringProperty time;
	private SimpleStringProperty id;
	private SimpleStringProperty length;
	private SimpleStringProperty data;
	
	/**
	 * 
	 */
	public CanTable(String time, String id, String length, String data) {
		
		this.time = new SimpleStringProperty(time);
		this.id = new SimpleStringProperty(id);
		this.length = new SimpleStringProperty(length);
		this.data = new SimpleStringProperty(data);
	}
	
	/**
	 * @return
	 */
	public String getTime() {
		return time.get();
	}
	
	/**
	 * @return
	 */
	public String getId() {
		return id.get();
	}
	
	/**
	 * @return
	 */
	public String getLength() {
		return length.get();
	}
	
	/**
	 * @return
	 */
	public String getData() {
		return data.get();
	}
	
	/**
	 * @param time
	 */
	public void setTime(String time) {
		this.time.set(time);
	}
	
	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id.set(id);
	}
	
	/**
	 * @param length
	 */
	public void setLength(String length) {
		this.length.set(length);
	}
	
	/**
	 * @param data
	 */
	public void setData(String data) {
		this.data.set(data);
	}
	
	
	
}
