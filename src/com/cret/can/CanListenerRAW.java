package com.cret.can;

import java.util.ArrayList;
import java.util.List;

import com.cret.staticdata.structures;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;


/**
 * 
 * CAN Message listener for RAW option.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class CanListenerRAW implements CANMessageListener {

	private CanTable lol;
	
	private long baseTimeStamp = 0L;
	
	private long time = 0L;
	
	public CanListenerRAW() {
		baseTimeStamp = System.currentTimeMillis();
	}
	
	List<String> currentID = new ArrayList<>();
	
	
	@Override
	public void receiveCANMessage(CANMessage msg) {
		
		String id = CanUtils.decToHex(msg.getId());
		String length = String.valueOf(msg.getData().length);
		String data = "";
		String ascii = "";
		
		for (Byte b : msg.getData()) {
			data = data + " " + String.format("%02x", b).toUpperCase();
			ascii = ascii + CanUtils.convertHexToAscii(String.format("%02x", b));
		}
		
		
		if (structures.monitor1) {
			
			if (currentID.contains(id)) {
				for (CanTable table : structures.dataTableMonitor1) {
					if (table.getId().equals(id)) {
						
						table.setData(data);
						table.setLength(length);
						table.setAscii(ascii);
					}
				}
				
			} else {
				currentID.add(id);
				structures.dataTableMonitor1.add(new CanTable(time, id, length, data, ascii));
			}

			
			
		} else {
			time = System.currentTimeMillis() - baseTimeStamp;
			structures.dataTable1.add(new CanTable(time, id, length, data, ascii));
			if (structures.dataTable1.size() > 1000) {
				structures.dataTable1.remove(0);
			}
		}
	}
}
