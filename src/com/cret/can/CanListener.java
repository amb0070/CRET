package com.cret.can;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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

	private Byte zero = new Byte("00");

	private int len = 0;

	private RootViewController controller;
	
	private String id = "";
	
	List<String> keySet;

	public CanListener(RootViewController controller) {
		this.controller = controller;
		
		if (RootViewController.mode.equals("Dashboard")) {
			
			List<String> keys = new ArrayList<>();
			keys.addAll(RootViewController.valuesInDashboard.keySet());
			System.out.println("Dentro de listener dash");
			for (String key : keys) {
				String id = RootViewController.valuesInDashboard.get(key).get(0); //ID
				RootViewController.addIDDash(id, 8);
			}
			
		}
	}

	@Override
	public void receiveCANMessage(CANMessage arg0) {

		
		if (RootViewController.mode.equals("Analysis")) {
			storeCanId(arg0);
		} else { //Dashboard
			//filterCanId(arg0);
		}
		//Check if it's project or analysis
		
	}
	
	private void filterCanId(CANMessage msg) {
		
		keySet = new ArrayList<>();
		
		id = CanUtils.decToHex(msg.getId());
		len = msg.getData().length;
		
		
		
		keySet.addAll(RootViewController.valuesInDashboard.keySet());
		
		for (String key : keySet) {
			
			
			if (controller.valuesInDashboard.get(key).contains(id)) {
			
				index = 0;
		for (Byte b : msg.getData()) {
			
			
			if (controller.valuesInDashboard.get(key).get(1).equals(String.valueOf(index))) {
				
			
			
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
				RootViewController.dataQ1Dash.get(id).get(index).add(CanUtils.hexToDec(b));
			}
			
		}
			index++;
		}
		}
		}
	}

	private void storeCanId(CANMessage msg) {


		id = CanUtils.decToHex(msg.getId());
		
		
		if (CanUtils.getSplitBytes()) { // Split
			len = msg.getData().length * 2;
		} else {
			len = msg.getData().length;
		}

		//Check if is in map. If not, add it
		index = 0;
		
		
		//For every byte
		for (Byte b : msg.getData()) {

			if (!b.equals(zero)) {
				
			
			if (!CanUtils.isIdentifiedInterface1Len(id)) { // ID NO IDENTIFICADA
				controller.addID(id, len + 5);
				System.out.println("ID NO IDENTIFICADA: " + id);
				CanUtils.setIdentifiedIdInterface1Len(id, len);

			}
			
			if (CanUtils.getSplitBytes()) {

				if (!CanUtils.isIdentifiedInterface1(id, index * 2)) {// ID - BYTE NO IDENTIFICADOS

					if (CanUtils.getIgnoreZeroBytes()) { // Bytes que son 0 y e ignorar.

						CanUtils.setIdentifiedIdInterface1(id, index * 2);
						CanUtils.setIdentifiedIdInterface1(id, (index * 2) + 1);

						System.out.println("New ID!: " + id);
						System.out.println("Byte: " + index);
						System.out.println("Length: " + len);

						Platform.runLater(() -> {
							controller.addNewCell(id, index * 2);
							controller.addNewCell(id, (index * 2) + 1);

						});

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else { // No ignore zero bytes
						CanUtils.setIdentifiedIdInterface1(id, index * 2);
						CanUtils.setIdentifiedIdInterface1(id, (index * 2) + 1);

						System.out.println("New ID!: " + id);
						System.out.println("Byte: " + index);
						System.out.println("Length: " + len);

						Platform.runLater(() -> {
							controller.addNewCell(id, index * 2);
							controller.addNewCell(id, (index * 2) + 1);

						});

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else { // Ya esta añadido, solo meto los datos en el observablelist

					String strByte = String.format("%02x", b);
					final int middle = strByte.length() / 2;
					String firstNibble = strByte.substring(0, middle);
					String secondNibble = strByte.substring(middle);
//System.out.println("-> " + strByte);
					RootViewController.dataQ1.get(id).get(index * 2).add(CanUtils.hexToDec(firstNibble));
					RootViewController.dataQ1.get(id).get((index * 2) + 1).add(CanUtils.hexToDec(secondNibble));
					
//RootViewController.dataQ1.get(id).get(index).add(CanUtils.hexToDec(b));

				}
				index++;
				
			} else { //BYTES WITHOUT SPLIT
				
				if (!CanUtils.isIdentifiedInterface1(id, index)) {// ID - BYTE NO IDENTIFICADOS

					if (CanUtils.getIgnoreZeroBytes()) { // Bytes que son 0 y e ignorar.

						CanUtils.setIdentifiedIdInterface1(id, index);

						System.out.println("New ID!: " + id);
						System.out.println("Byte: " + index);
						System.out.println("Length: " + len);

						Platform.runLater(() -> {
							controller.addNewCell(id, index);
						});

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else { // No ignore zero bytes
						
						CanUtils.setIdentifiedIdInterface1(id, index);

						System.out.println("New ID!: " + id);
						System.out.println("Byte: " + index);
						System.out.println("Length: " + len);

						Platform.runLater(() -> {
							
							controller.addNewCell(id, index);

						});

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else { // Ya esta añadido, solo meto los datos en el observablelist


					//RootViewController.dataQ1.get(id).get(index * 2).add(CanUtils.hexToDec(firstNibble));
					//RootViewController.dataQ1.get(id).get((index * 2) + 1).add(CanUtils.hexToDec(secondNibble));
					RootViewController.dataQ1.get(id).get(index).add(CanUtils.hexToDec(b));

					
					
					
				}
				index++;
			}
		}else {
			//System.out.println("Byte " + id + " " + index + " es 0");
		} 
			
		}
	}
}
