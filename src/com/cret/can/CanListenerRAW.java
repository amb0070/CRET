package com.cret.can;

import com.cret.staticData.structures;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;

public class CanListenerRAW implements CANMessageListener {

	@Override
	public void receiveCANMessage(CANMessage msg) {
		String id = String.valueOf(msg.getId());
		String length = String.valueOf(msg.getData().length);
		String data = "";
		for (Byte b : msg.getData()) {
			data = data + b.toString();
		}
		
		System.out.println("ID " + id + " LENGTH " + length + " DATA " + data);
		structures.dataTable.add(new CanTable("TIME", id, length, data));
		
	}

}
