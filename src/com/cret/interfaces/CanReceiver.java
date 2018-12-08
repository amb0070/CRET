package com.cret.interfaces;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;

public class CanReceiver  implements CANMessageListener{

	@Override
	public void receiveCANMessage(CANMessage msg) {
		
		System.out.println("Message: " + msg);
		System.out.print("ID: " + msg.getId() + " len: " + msg.getData().length + " data: " );
		for (byte b : msg.getData()) {
			System.out.print(String.format("%02x",b)); 
		}
	}
	
}
