package com.cret.can;

import com.cret.gui.GuiUtils;
import com.cret.gui.RootViewController;
import com.cret.staticdata.structures;

import de.fischl.usbtin.CANMessage;
import de.fischl.usbtin.CANMessageListener;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * CAN Message listener for analysis option.
 * 
 * Two main options, ignore bytes with value 0 and split bytes.
 * 
 * @author Adrian Marcos
 * @version 1.0
 * 
 */
class CanListenerAnalysis implements CANMessageListener {
	
	
	static final String INTERNAL_ERROR = "INTERNAL ERROR";
	
	/**
	 * Index of the data field.
	 */
	private int index = 0;

	/**
	 * Byte with 00 value.
	 */
	private Byte zero = new Byte("00");

	/**
	 * Message length
	 */
	private int len = 0;

	/**
	 * Main controller to modify elements from another thread.
	 */
	private RootViewController controller;

	/**
	 * Constructor
	 * 
	 * Get's the controller of RootView to modify from another thread.
	 * 
	 * @param controller RootViewController of the application.
	 */
	CanListenerAnalysis(RootViewController controller) {
		this.controller = controller;
	}

	@Override
	public void receiveCANMessage(CANMessage message) {

		filterMessage(message);

	}

	/**
	 * 
	 * Applyes filters to the message.
	 * 
	 * Options: Ignore byte with value 00 or split bytes.
	 * 
	 * @param msg CAN Message to filter.
	 */
	private void filterMessage(CANMessage msg) {

		// Get the ID of the message.
		String id = CanUtils.decToHex(msg.getId());

		index = 0;

		// For every byte in data field
		for (Byte b : msg.getData()) {

			/**
			 * If option "split bytes" is selected
			 */
			if (CanUtils.getSplitBytesInterface1()) { // SPLIT BYTES

				// Length * 2 (bytes are splitted
				len = msg.getData().length * 2;

				// Not identified ID
				if (!CanUtils.isIdentifiedInterface1Len(id)) {
					// Add id to structures
					controller.addID(id, len);
					CanUtils.setIdentifiedIdInterface1Len(id, len);
				}
				/**
				 * If option ignore zero bytes is selected.
				 */
				if (CanUtils.getIgnoreZeroBytesInterface1()) { // IGNORE ZERO - SPLIT

					String strByte = String.format("%02x", b);
					final int middle = strByte.length() / 2;
					String firstNibble = strByte.substring(0, middle);
					String secondNibble = strByte.substring(middle);

					// NOT 0
					if (!CanUtils.isIdentifiedInterface1(id, (index * 2)) && !firstNibble.equals("0")
							&& !secondNibble.equals("0")) {

						CanUtils.setIdentifiedIdInterface1(id, (index * 2));
						CanUtils.setIdentifiedIdInterface1(id, ((index * 2) + 1));

						Platform.runLater(() -> {
							controller.addNewCell(id, (index * 2));
							controller.addNewCell(id, ((index * 2) + 1));
						});

						try {
							Thread.sleep(700);
						} catch (InterruptedException e) {
							GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
							Thread.currentThread().interrupt();
						}
					} else { // BYTE IS KNOWN

						controller.progressRoot.setVisible(false);

						structures.dataQ1.get(id).get((index * 2)).add(CanUtils.hexToDec(firstNibble));
						structures.dataQ1.get(id).get(((index * 2) + 1)).add(CanUtils.hexToDec(secondNibble));
					}

					/**
					 * If option ignore zero bytes is not selected.
					 */
				} else { // DON IGNORE ZERO BYTES

					String strByte = String.format("%02x", b);
					final int middle = strByte.length() / 2;
					String firstNibble = strByte.substring(0, middle);
					String secondNibble = strByte.substring(middle);

					if (!CanUtils.isIdentifiedInterface1(id, (index * 2))) {

						CanUtils.setIdentifiedIdInterface1(id, (index * 2));
						CanUtils.setIdentifiedIdInterface1(id, ((index * 2) + 1));

						Platform.runLater(() -> {
							controller.addNewCell(id, (index * 2));
							controller.addNewCell(id, ((index * 2) + 1));

						});

						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
							Thread.currentThread().interrupt();
						}

					} else { // BYTE IS KNOWN

						// Hides the progress indicator of the main controller.
						controller.progressRoot.setVisible(false);

						// Add data to structure
						structures.dataQ1.get(id).get((index * 2)).add(CanUtils.hexToDec(firstNibble));
						structures.dataQ1.get(id).get(((index * 2) + 1)).add(CanUtils.hexToDec(secondNibble));
					}

				}

				/**
				 * If option split bytes is not selected.
				 */
			} else { // DON'T SPLIT BYTES

				len = msg.getData().length;

				if (!CanUtils.isIdentifiedInterface1Len(id)) {
					controller.addID(id, len);
					CanUtils.setIdentifiedIdInterface1Len(id, len);
				}

				/**
				 * If option ignore zero bytes is selected.
				 */
				if (CanUtils.getIgnoreZeroBytesInterface1()) { // IGNORE ZERO - NO SPLIT

					if (!CanUtils.isIdentifiedInterface1(id, index) && !b.equals(zero)) {

						CanUtils.setIdentifiedIdInterface1(id, index);

						Platform.runLater(() -> {
							controller.addNewCell(id, index);
						});

						try {
							Thread.sleep(700);
						} catch (InterruptedException e) {
							GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
							Thread.currentThread().interrupt();
						}
					} else { // BYTE IS KNOWN

						controller.progressRoot.setVisible(false);

						// Add data to structure
						structures.dataQ1.get(id).get(index).add(CanUtils.hexToDec(b));
					}

					/**
					 * If option ignore zero bytes is not selected.
					 */
				} else { // DON IGNORE ZERO BYTES

					if (!CanUtils.isIdentifiedInterface1(id, index)) {

						CanUtils.setIdentifiedIdInterface1(id, index);

						Platform.runLater(() -> {
							controller.addNewCell(id, index);
						});

						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							GuiUtils.generateAlert(AlertType.ERROR, INTERNAL_ERROR, "Thread error.");
							Thread.currentThread().interrupt();
						}
					} else { // BYTE IS KNOWN
						// ADD DATA
						controller.progressRoot.setVisible(false);
						structures.dataQ1.get(id).get(index).add(CanUtils.hexToDec(b));
					}

				} // END OF IF IGNORE ZERO

			} // END OF SPLIT BYTES
			index++;

		} // END OF BUCLE FOR
	} // END OF FUNCITON
} // END OF CLASS
