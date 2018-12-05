package com.cret.interfaces;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtin.OpenMode;
import de.fischl.usbtin.USBtinException;


public class CANCon implements CANMessageListener{

	protected USBtin connection;
	
	public CANCon() {
		
		connection = new USBtin();
		
		//connection.addMessageListener(this);
		
	}
	
	@Override
	public void receiveCANMessage(CANMessage msg) {
		
		System.out.println("Message: " + msg);
		System.out.println("ID: " + msg.getId() + " len: " + msg.getData().length + " data: " + msg.getData().toString() );
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
