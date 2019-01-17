package com.cret.can;

import java.util.ArrayList;
import java.util.List;

import com.cret.gui.RootViewController;
import com.cret.staticData.structures;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import javafx.application.Platform;

public class CanListenerDashboard implements CANMessageListener {


	private int index = 0;

	private Byte zero = new Byte("00");

	private int len = 0;

	private RootViewController controller;
	
	private String id = "";
	
	List<String> keySet;

	public CanListenerDashboard(RootViewController controller) {
		this.controller = controller;
		
			
			List<String> keys = new ArrayList<>();
			keys.addAll(structures.valuesInDashboard.keySet());
			System.out.println("Dentro de listener dash");
			for (String key : keys) {
				String id = structures.valuesInDashboard.get(key).get(0); //ID
				RootViewController.addIDDash(id, 8);
			}

	}

	@Override
	public void receiveCANMessage(CANMessage arg0) {

		
			filterCanId(arg0);

	}
	
	private void filterCanId(CANMessage msg) {
		
		keySet = new ArrayList<>();
		
		id = CanUtils.decToHex(msg.getId());
		len = msg.getData().length;
		
		
		
		keySet.addAll(structures.valuesInDashboard.keySet());
		
		for (String key : keySet) {
			
			
			if (structures.valuesInDashboard.get(key).contains(id)) {
			
				index = 0;
		for (Byte b : msg.getData()) {
			
			
			if (structures.valuesInDashboard.get(key).get(1).equals(String.valueOf(index))) {
				
			
			
			if(!CanUtils.isIdentifiedInterface1Len(id)) {
				
				controller.addIDDash(id, len+5);
				
				CanUtils.setIdentifiedIdInterface1Len(id, len);
			}
			
			if (!CanUtils.isIdentifiedInterface1(id, index)) {// ID - BYTE NO IDENTIFICADOS

					
					CanUtils.setIdentifiedIdInterface1(id, index);

					System.out.println("New ID!: " + id);
					System.out.println("Byte: " + index);
					System.out.println("Length: " + len);

					Platform.runLater(() -> {
						
						controller.addNewCellToDashboard(id, index, key);
					});

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else {
				structures.dataQ1Dash.get(id).get(index).add(CanUtils.hexToDec(b));
			}
			
		}
			index++;
		}
		}
		}
	}

}
