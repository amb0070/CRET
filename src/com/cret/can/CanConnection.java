package com.cret.can;

import com.cret.gui.GuiUtils;
import com.cret.gui.RootViewController;
import com.cret.staticData.structures;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtin.OpenMode;
import javafx.scene.control.Alert.AlertType;
import de.fischl.usbtin.USBtinException;

/**
 * CanConnection.java - Connection module to CAN Bus network.
 * 
 * Object to connect to CAN network. 3 possible listeners to use.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class CanConnection{

	/**
	 * Port to connect.
	 */
	private String port;
	
	/**
	 * Speed of the connection.
	 */
	private int speed;
	
	/**
	 * Connection mode. (ACTIVE, LOOPBACK and LISTENONLY)
	 */
	private OpenMode mode;
	
	/**
	 * Object to connect to CAN network.
	 */
	private USBtin connection;
	
	/**
	 * Listener for analysis connection.
	 */
	private CanListenerAnalysis listenerAnalysis;
	
	/**
	 * Listener for dashboard connection.
	 */
	private CanListenerDashboard listenerDashboard;
	
	/**
	 * Listener for RAW connection.
	 */
	private CanListenerRAW listenerRAW;
	
	/**
	 * Main controller. Used to set elements from another thread.
	 */
	private RootViewController controller;
	
	
	/**
	 * 
	 * Constructor. Sets the port, speed and mode to connect to CAN network.
	 * 
	 * @param port Port to connect.
	 * @param speed Speed of the connection.
	 * @param mode Mode of the connection. (ACTIVE, LISTEN-ONLY or LOOPBACK).
	 * @param controller Main FXML controller. Used to modify the GUI from this class.
	 * @throws USBtinException Exception if there is any error connecting to the port.
	 */
	public CanConnection(String port, String speed, String mode, RootViewController controller) throws USBtinException {
		
		
		this.controller = controller;
		
		this.port = port;
		
		this.speed = Integer.parseInt(speed);
		
		if (mode.equals("ACTIVE")) {
			
			this.mode = OpenMode.ACTIVE;
			
		} else if (mode.equals("LISTENONLY")) {
			
			this.mode = OpenMode.LISTENONLY;
			
		} else if (mode.equals("LOOPBACK")) {
			
			this.mode = OpenMode.LOOPBACK;
			
		} else {
			
			//Default mode: LISTENONLY.
			this.mode = OpenMode.LISTENONLY;
			
		}
		
		connection = new USBtin();
		
		/*
		 * Sets the listener.
		 */
		if (structures.modeInterface1.equals("Dashboard")) {
			
			listenerDashboard = new CanListenerDashboard(controller);
			connection.addMessageListener(listenerDashboard);
			
		} else if (structures.modeInterface1.equals("Analysis")) {
			
			listenerAnalysis = new CanListenerAnalysis(controller);
			connection.addMessageListener(listenerAnalysis);
			
		} else if (structures.modeInterface1.equals("RAW")) {
			
			listenerRAW = new CanListenerRAW();
			connection.addMessageListener(listenerRAW);
			
		} else {
			GuiUtils.generateAlert(AlertType.ERROR, "INTERNAL ERROR", "Selected option is not valid.");
		}

	}
	

	/**
	 * Connect to to CAN network.
	 */
	public void connect() {
		
		try {
			
			connection.connect(port);
			connection.openCANChannel(speed, mode);
			
		} catch (USBtinException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "PORT ERROR", "Port is busy.");
		}
		
	}
	
	/**
	 * Disconnect from CAN network.
	 * 
	 * @throws USBtinException If can't disconnect from port.
	 */
	public void disconnect() throws USBtinException {
		
		// Close CAN Channel
		connection.closeCANChannel();
		
		//Disconnect from port.
		connection.disconnect();
		
		connection = null;
		listenerAnalysis = null;
		listenerDashboard = null;
	}
}
