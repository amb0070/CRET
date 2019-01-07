package com.cret.interfaces;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;

public class CanReceiver  implements CANMessageListener{

	
	
	@Override
	public void receiveCANMessage(CANMessage msg) {
		System.out.println(msg);
		

	}
	
	

	
	public void printMessage(CANMessage msg) {
		System.out.println(msg);
	}
}
