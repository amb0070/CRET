package com.cret.can;

import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import com.cret.gui.RootViewController;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

public class CanListener implements CANMessageListener {
	

	private int index = 0;


	private RootViewController controller;
	
	public CanListener(RootViewController controller) {
		this.controller = controller;
	}
	
	
	@Override
	public void receiveCANMessage(CANMessage arg0) {

		storeCanId(arg0);
	}
	

	private void storeCanId(CANMessage msg) {
		
		String id = CanUtils.decToHex(msg.getId());
		int len = msg.getData().length;
		
		//Check if is in map. If not, add it
		
		if (!CanUtils.isIdentifiedInterface1(id)) {
			
			CanUtils.setIdentifiedIdInterface1(id, len);
			System.out.println("New ID!: " + id);
			
			Platform.runLater(()->{
				
				controller.addID(id, len);
				int index = 0;
				for (Byte i : msg.getData()) {
						controller.addNewCell(id, index);
						index++;
				}

			});
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else { //Ya esta añadido, solo meto los datos en el observablelist

				index = 0;
				
				for (Byte b : msg.getData()) {
					
					if (!b.equals(00)) {
					
					RootViewController.dataQ1.get(id).get(index).add(CanUtils.hexToDec(b));
					}
					index++;
				}


		}
		
	}

}
