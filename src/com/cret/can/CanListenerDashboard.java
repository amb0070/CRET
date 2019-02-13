package com.cret.can;

import java.util.ArrayList;
import java.util.List;
import com.cret.gui.RootViewController;
import com.cret.staticData.structures;
import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import javafx.application.Platform;

/**
 * 
 * CAN Message listener for dashboard option.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class CanListenerDashboard implements CANMessageListener {

	private int index = 0;

	private int len = 0;

	private RootViewController controller;

	private String id = "";

	List<String> keySet;

	public CanListenerDashboard(RootViewController controller) {
		this.controller = controller;

		List<String> keys = new ArrayList<>();
		keySet = new ArrayList<>();
		
		keys.addAll(structures.valuesInDashboard1.keySet());
		
		for (String key : keys) {
			
			String id = structures.valuesInDashboard1.get(key).get(0);
			System.out.println("Aqui andamios: " + id);
			controller.addIDDash(id, 20);
		}
		
		keySet.addAll(structures.valuesInDashboard1.keySet());

	}

	@Override
	public void receiveCANMessage(CANMessage arg0) {

		filterCanId(arg0);
	}

	private void filterCanId(CANMessage msg) {


		id = CanUtils.decToHex(msg.getId());
		
		len = msg.getData().length;

		for (String key : keySet) {
			
			if (structures.valuesInDashboard1.get(key).contains(id)) {
				index = 0;
				
				for (Byte b : msg.getData()) {

					if (structures.valuesInDashboard1.get(key).get(1).equals(String.valueOf(index))) {

						if (!CanUtils.isIdentifiedInterface1Len(id)) {

							controller.addIDDash(id, len);
							System.out.println("Añadiendo: " + id + " " + len);

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
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							controller.progressRoot.setVisible(false);
							
							structures.dataQ1Dash1.get(id).get(index).add(CanUtils.hexToDec(b));
						}
					}
					index++;
				}
			}
		}
	}

}
