package com.cret.can;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * Class used to create the necessary structure to show data on a tableView.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class CanTable {

	/**
	 * Frame time.
	 */
	private SimpleLongProperty time;
	/**
	 * Identifier of the frame.
	 */
	private SimpleStringProperty id;
	
	/**
	 * Length of the data of the frame.
	 */
	private SimpleStringProperty length;
	
	/**
	 * Data of the frame.
	 */
	private SimpleStringProperty data;
	
	/**
	 * ASCII value of data field.
	 */
	private SimpleStringProperty ascii;

	/**
	 * 
	 * Constructor. Sets the parameters of a CAN frame.
	 * 
	 * @param time Time of the received frame.
	 * @param id Identifier of the frame.
	 * @param length Length of data field of the frame.
	 * @param data Data field.
	 * @param ascii ASCII value of data field.
	 * 
	 */
	public CanTable(long time, String id, String length, String data, String ascii) {
		
		this.time = new SimpleLongProperty(time);
		this.id = new SimpleStringProperty(id);
		this.length = new SimpleStringProperty(length);
		this.data = new SimpleStringProperty(data);
		this.ascii = new SimpleStringProperty(ascii);
	}
	
	/**
	 * Public method to set the ASCII value.
	 * 
	 * @param ascii ASCII value of data field.
	 * 
	 */
	public void setAscii(String ascii) {
		this.ascii.set(ascii);
	}
	

	/**
	 * Returns ascii value of the current frame.
	 * 
	 * @return ASCII value of current frame.
	 * 
	 */
	public String getAscii() {
		return ascii.get();
	}
	
	/**
	 * Returns the time of the current frame.
	 * 
	 * @return Time of the current frame.
	 * 
	 */
	public long getTime() {
		return time.get();
	}
	
	/**
	 * Returns the identifier of the current frame.
	 * 
	 * @return Identifier of the current frame.
	 * 
	 */
	public String getId() {
		return id.get();
	}
	
	/**
	 * Returns the length of the data field.
	 * 
	 * @return Length of the data field.
	 * 
	 */
	public String getLength() {
		return length.get();
	}
	
	/**
	 * 
	 * Returns the data of the current frame.
	 * 
	 * @return Data of the current frame.
	 * 
	 */
	public String getData() {
		return data.get();
	}
	
	/**
	 * Sets the time of the frame.
	 * 
	 * @param time Time of the frame.
	 * 
	 */
	public void setTime(long time) {
		this.time.set(time);
	}
	
	/**
	 * Sets the ID of the frame.
	 * 
	 * @param id Identifier of the frame.
	 * 
	 */
	public void setId(String id) {
		this.id.set(id);
	}
	
	/**
	 * Sets the length of the data field.
	 * 
	 * @param length Length of the data field.
	 * 
	 */
	public void setLength(String length) {
		this.length.set(length);
	}
	
	/**
	 * Sets the data o the frame.
	 * 
	 * @param data Data of the frame.
	 * 
	 */
	public void setData(String data) {
		this.data.set(data);
	}
	
}
