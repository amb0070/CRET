package com.cret.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cret.gui.RootViewController;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

public class listener implements CANMessageListener {
	
	String id = "";

	SimpleDateFormat dateFormat = new SimpleDateFormat("ss.SSSS");

	int par = 0;

	byte ignoreByte = 00;
	
	private Task<CANMessage> task;
	
	int byteNumber = 0;
	
	RootViewController controller;
	
	boolean flag = true;
	
	public listener(RootViewController controller) {
		this.controller = controller;
	}
	
	
	@Override
	public void receiveCANMessage(CANMessage arg0) {
		
		storeCanId(arg0);

		
	}
	

	public void storeCanId(CANMessage msg) {
		
		String id = CanUtils.decToHex(msg.getId());
		int len = msg.getData().length;
		
		//Check if is in map. If not, add it
		
		if (!CanUtils.isIdentifiedInterface1(id)) {
			CanUtils.setIdentifiedIdInterface1(id, len);
			System.out.println("New ID!: " + id);
			Platform.runLater(()->{
				controller.addID(id, len+2);
				int index = 0;
				for (Byte i : msg.getData()) {
					//if (!i.equals(00)) {
						controller.addNewCell(id, index);
						//System.out.println("NUMEROOOO " + index);
						index++;
					//}

				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("CREADO DE: "  + index);
			});
			
			
		} else { //Ya esta añadido, solo meto los datos en el observablelist
			
			if (par==2) {
				int index = 0;
				String strDate = dateFormat.format(new Date());
				//System.out.println("Entrando...");
				for (Byte b : msg.getData()) {
				//	if (!b.equals(ignoreByte)) {
					//System.out.println("Ahi va con " + index);
					RootViewController.blocked = true;
	        		RootViewController.myXaxisCategories.get(id).get(index).add(strDate);
					RootViewController.valuesToPlot.get(id).get(index).add(new XYChart.Data(strDate, Integer.valueOf(CanUtils.hexToDec(b))));
					RootViewController.blocked = false;
					index++;
					//}
					//System.out.println("EJECUTA BIEN ALGUNA VEZ BRO");
				}
				
				RootViewController.valuesToPlot.get(id).get(byteNumber).addListener((ListChangeListener<XYChart.Data<String, Integer>>) change -> {
			        
					
					if (change.getList().size() - RootViewController.lastObservedSize.get(id).get(byteNumber) > 10) {
			        	RootViewController.lastObservedSize.get(id).set(byteNumber, RootViewController.lastObservedSize.get(id).get(byteNumber) + 1);
			            	
			            RootViewController.xAxis.get(id).get(byteNumber).getCategories().remove(0,1);

			            }
			        });
				
				par = 0;
			} else {
				par = par + 1;
			}

		}
		
	}

}
