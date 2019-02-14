package com.cret.can;

import java.util.ArrayList;
import java.util.List;
import com.cret.gui.GuiUtils;
import com.cret.gui.RootViewController;
import com.cret.staticdata.structures;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * CAN Message listener for dashboard option.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class CanListenerDashboard implements CANMessageListener {

	
	private static final String INTERNAL_ERROR = "INTERNAL ERROR";
	
	private int index = 0;

	private int len = 0;

	private RootViewController controller;

	private String id = "";

	List<String> keySet;

	/**
	 * Constructor of CanListenerDashboard
	 * @param controller RootViewController to modify elements from other thread.
	 */
	public CanListenerDashboard(RootViewController controller) {
		this.controller = controller;

		List<String> keys = new ArrayList<>();
		keySet = new ArrayList<>();
		
		keys.addAll(structures.valuesInDashboard1.keySet());
		
		for (String key : keys) {
			
			String tempId = structures.valuesInDashboard1.get(key).get(0);
			controller.addIDDashboard1(tempId, 20);
		}
		
		keySet.addAll(structures.valuesInDashboard1.keySet());

	}

	@Override
	public void receiveCANMessage(CANMessage arg0) {

		filterCanId(arg0);
	}

	/**
	 * Filters every CAN Frame.
	 * @param msg CANMessage to filter.
	 */
	private void filterCanId(CANMessage msg) {


		id = CanUtils.decToHex(msg.getId());
		
		len = msg.getData().length;

		for (String key : keySet) {
			
			if (structures.valuesInDashboard1.get(key).contains(id)) {
				index = 0;
				
				for (Byte b : msg.getData()) {

					if (structures.valuesInDashboard1.get(key).get(1).equals(String.valueOf(index))) {

						if (!CanUtils.isIdentifiedInterface1Len(id)) {

							controller.addIDDashboard1(id, len);

							CanUtils.setIdentifiedIdInterface1Len(id, len);
						}

						if (!CanUtils.isIdentifiedInterface1(id, index)) {// ID - BYTE NO IDENTIFICADOS

							CanUtils.setIdentifiedIdInterface1(id, index);
							
							Platform.runLater(() -> {
								controller.addNewCellToDashboard1(id, index, key);
							});

							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
								Thread.currentThread().interrupt();
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
