package com.cret.can;

import com.cret.staticData.structures;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;



public class CanListenerRAW implements CANMessageListener {

	
	private CanTable lol;
	
	@Override
	public void receiveCANMessage(CANMessage msg) {
		String id = CanUtils.decToHex(msg.getId());
		String length = String.valueOf(msg.getData().length);
		String data = "";
		for (Byte b : msg.getData()) {
			data = data + " " + String.format("%02x", b).toUpperCase();
		}
		
		//System.out.println("ID " + id + " LENGTH " + length + " DATA " + data);
		
		structures.dataTable.add(new CanTable("TIME", id, length, data));
		if (structures.dataTable.size() > 1000) {
			structures.dataTable.remove(0);
		}
		
		if (structures.monitor) {
			lol = new CanTable("", id, "", "");
				if (structures.dataTable.contains(lol.getId())) {
					System.out.println("ID REPETIDA"); 
				}
		}
		
		
	}

}
