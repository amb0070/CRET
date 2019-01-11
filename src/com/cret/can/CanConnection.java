package com.cret.can;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.cret.gui.GuiUtils;
import com.cret.gui.RootViewController;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtin.OpenMode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import de.fischl.usbtin.USBtinException;


public class CanConnection{

	private String port;
	private int speed;
	private OpenMode mode;
	
	protected USBtin connection;
	
	private CanListener listener;
	
	RootViewController controller;

	
	
	public CanConnection(String port, String speed, String mode, RootViewController controller) throws IOException, USBtinException {
		
		
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
			this.mode = OpenMode.LISTENONLY;
		}
		
		
		
		connection = new USBtin();
		
		listener = new CanListener(controller);
		connection.addMessageListener(listener);

	}
	

	

	public void connectToPort() {
		try {
			
			connection.connect(port);
			connection.openCANChannel(speed, mode);
			controller.startButton.setDisable(true);
			controller.stopButton.setDisable(false);
			//Connected
			
		} catch (USBtinException e) {
			GuiUtils.generateAlert(AlertType.ERROR, "COM PORT ERROR", "COM Port is busy.");
		}
		
	}
	
	
	public void disconnect() throws USBtinException {
		connection.closeCANChannel();
		connection.disconnect();
		connection = null;
	}
	
	public void disconnectAll() {
		
	}

	

}
