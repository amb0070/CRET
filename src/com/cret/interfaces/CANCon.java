package com.cret.interfaces;

import java.io.IOException;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtin.OpenMode;
import de.fischl.usbtin.USBtinException;


public class CANCon extends Thread{

	protected USBtin connection;
	protected CanReceiver receiver;
	public CANCon() throws IOException, USBtinException {
		
		connection = new USBtin();
		receiver = new CanReceiver();

	}
	
	public void run() {
		connectToPort("COM5", 125000, OpenMode.ACTIVE);
		//connection.addMessageListener(this);
		connection.addMessageListener(receiver);
		
		System.out.println("ready");
        try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // close the CAN channel and close the connection
        try {
			disconnect();
		} catch (USBtinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	

}
