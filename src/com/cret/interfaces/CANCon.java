package com.cret.interfaces;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import de.fischl.usbtin.USBtinException;


public class CANCon extends Thread{

	private String port;
	private int speed;
	private OpenMode mode;
	
	protected USBtin connection;
	protected CanReceiver receiver;
	

	
	public CANCon(String port, String speed, String mode) throws IOException, USBtinException {
		
		
		
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
		receiver = new CanReceiver();
		

	}
	

	

	private void connectToPort(String port, int speed, OpenMode mode) {
		try {
			
			connection.connect(port);
			connection.openCANChannel(speed, mode);
			//Connected
			
		} catch (USBtinException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	
	public void disconnect() throws USBtinException {
		connection.closeCANChannel();
		connection.disconnect();
	}
	
	public void disconnectAll() {
		
	}

	public void run() {
		

		connectToPort(port, speed, mode);

		connection.addMessageListener(new CANMessageListener() {

			@Override
			public void receiveCANMessage(CANMessage msg) {
				
				//When can receives a message
				
				
				//DO IT
				

				storeCanId(msg);
				printMessage(msg);

			}
			
			public void printMessage(CANMessage msg) {
				//System.out.println("Message: " + msg);
				System.out.print("ID: " + msg.getId() + " len: " + msg.getData().length + " data: " );
				for (byte b : msg.getData()) {
					System.out.print(String.format("%02x",b));
				}
				

				
				System.out.println();
			}
			

			
			
			
			public void storeCanId(CANMessage msg) {
				
				String id = CanUtils.decToHex(msg.getId());
				int len = msg.getData().length;
				
				//Check if is in map. If not, add it
				
				if (!CanUtils.isIdentifiedInterface1(id)) {
					CanUtils.setIdentifiedIdInterface1(id, len);
					System.out.println("New ID!: " + id);
					
					RootViewController.addID(id, len);
					
					
					
				} else { //Ya esta añadido, solo meto los datos en el observablelist

						
				}
				
			}
			
			
		});

		System.out.println("ready");
		

	}

	

}
