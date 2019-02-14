package com.cret.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.cret.can.CanUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * 
 * Controller for configuration window.
 * 
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */

public class ConfigurationViewController {

	/**
	 * ArrayList with the COM ports in use. Used to filter if one port is selected
	 * by another interface.
	 */

	private List<String> inUseCOMPorts = new ArrayList<>();

	/**
	 * FXML element.
	 * 
	 * Accordion used to choose between "Interface 1", "Interface 2" or
	 * "Configuration" menus.
	 */

	@FXML
	private Accordion accordationConfigure;

	/**
	 * FXML element.
	 * 
	 * ComboBox used to show and choose the COM port assigned to interface 1.
	 */

	@FXML
	private ComboBox<String> comboxPortsInterface1;

	/**
	 * FXML element.
	 * 
	 * ComboBox used to show and choose the COM port assigned to interface 2.
	 */

	@FXML
	private ComboBox<String> comboxPortsInterface2;

	/**
	 * FXML element.
	 * 
	 * ComboBox used to show and select the connection speed for interface 1.
	 */

	@FXML
	private ComboBox<String> comboxSpeedInterface1;

	/**
	 * FXML element.
	 * 
	 * ComboBox used to show and select the connection speed for interface 2.
	 */

	@FXML
	private ComboBox<String> comboxSpeedInterface2;

	/**
	 * FXML element.
	 * 
	 * ComboBox used to show and select the connection mode for interface 1.
	 */

	@FXML
	private ComboBox<String> comboxModeInterface1;

	/**
	 * FXML element.
	 * 
	 * ComboBox used to show and select the connection mode for interface 2.
	 */

	@FXML
	private ComboBox<String> comboxModeInterface2;

	/**
	 * FXML element.
	 * 
	 * Button to save data of the interface 1.
	 */

	@FXML
	private Button btnSaveInterface1;

	/**
	 * FXML element.
	 * 
	 * Button to save data of the interface 2.
	 */

	@FXML
	private Button btnSaveInterface2;

	/**
	 * FXML element.
	 * 
	 * Button to close configuration window.
	 */

	@FXML
	private Button btnCloseInterface1;

	/**
	 * FXML element.
	 * 
	 * Button to close configuration window.
	 */

	@FXML
	private Button btnCloseInterface2;

	/**
	 * FXML element.
	 * 
	 * Button to enable elements to configure interface 1.
	 */

	@FXML
	private ToggleButton btnEnableInterface1;

	/**
	 * FXML element.
	 * 
	 * Button to enable elements to configure interface 2.
	 */

	@FXML
	private ToggleButton btnEnableInterface2;

	/**
	 * FXML element.
	 * 
	 * CheckBox to ignore bytes with value 00.
	 */

	@FXML
	private CheckBox checkIgnoreZeroBytes;

	/**
	 * FXML element.
	 * 
	 * CheckBox to split bytes in 2 parts.
	 */

	@FXML
	private CheckBox checkSplitBytes;

	/**
	 * FXML element.
	 * 
	 * Button to save data of the configuration pane.
	 */

	@FXML
	private Button btnSaveConfig;

	/**
	 * FXML element.
	 * 
	 * Button to close configuration window.
	 */

	@FXML
	private Button btnCloseConfig;

	/**
	 * FXML element.
	 * 
	 * Interface 1 pane from the accordion.
	 */

	@FXML
	private TitledPane paneInterface1;

	/**
	 * FXML function.
	 * 
	 * When the window is loaded, this function is executed.
	 */

	private final String buttonStyle = "-fx-base: rgb(0, 255, 0);";

	@FXML
	public void initialize() {

		// Store found COM ports.
		List<String> serialPortList = new ArrayList<String>();

		// Call function to get COM ports and load it on serialPortList.
		Collections.addAll(serialPortList, CanUtils.getSerialPorts());

		// ObservableList to load COM ports on the ComboBox elements of the GUI.
		ObservableList<String> obList = FXCollections.observableList(serialPortList);

		// Add COM ports to ComboBox menus.
		comboxPortsInterface1.setItems(obList);
		comboxPortsInterface2.setItems(obList);

		// ObservableList with pre-configured speeds.
		ObservableList<String> connectionSpeedList = FXCollections.observableArrayList("10000", "20000", "50000",
				"100000", "125000", "250000", "500000", "800000", "1000000");

		// ObservableList with the connection available modes.
		ObservableList<String> connectionModes = FXCollections.observableArrayList("ACTIVE", "LISTENONLY", "LOOPBACK");

		// Add speed list to Combobox elements.
		comboxSpeedInterface1.setItems(connectionSpeedList);
		comboxSpeedInterface2.setItems(connectionSpeedList);

		// Add mode list to Combobox elements.
		comboxModeInterface1.setItems(connectionModes);
		comboxModeInterface2.setItems(connectionModes);

		/**
		 * Check when the window is opened, if there is previous configuration for
		 * interfaces.
		 */

		// If interface 1 is configured:
		if (CanUtils.getInterface1State()) {

			// Enable elements of the GUI.
			btnEnableInterface1.setSelected(true);
			comboxSpeedInterface1.setDisable(false);
			comboxPortsInterface1.setDisable(false);
			comboxModeInterface1.setDisable(false);

			// Load configuration data into elements of the GUI.
			comboxPortsInterface1.setValue(CanUtils.getConfigurationPortInterface1());
			comboxSpeedInterface1.setValue(CanUtils.getConfigurationSpeedInterface1());
			comboxModeInterface1.setValue(CanUtils.getConfigurationModeInterface1());
		}

		// If interface 2 is configured:
		if (CanUtils.getInterface2State()) {

			// Enable elements of the GUI.
			btnEnableInterface2.setSelected(true);
			comboxSpeedInterface2.setDisable(false);
			comboxPortsInterface2.setDisable(false);
			comboxModeInterface2.setDisable(false);

			// Load configuration data into elements of the GUI.
			comboxPortsInterface2.setValue(CanUtils.getConfigurationPortInterface2());
			comboxSpeedInterface2.setValue(CanUtils.getConfigurationSpeedInterface2());
			comboxModeInterface2.setValue(CanUtils.getConfigurationModeInterface2());
		}

		// If split bytes option is configured.
		if (CanUtils.getSplitBytesInterface1()) {
			checkSplitBytes.setSelected(true);
		} else {
			checkSplitBytes.setSelected(false);
		}

		// If ignore bytes with value 00 is conigured.
		if (CanUtils.getIgnoreZeroBytesInterface1()) {
			checkIgnoreZeroBytes.setSelected(true);
		} else {
			checkIgnoreZeroBytes.setSelected(false);
		}

		// Expand first pane of the accordation.
		accordationConfigure.setExpandedPane(paneInterface1);

	}

	/**
	 * FXML function.
	 * 
	 * Closes the configuration window.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	public void closeConfigurationWindow(ActionEvent event) {
		Stage stage = (Stage) btnCloseInterface1.getScene().getWindow();
		stage.close();
	}

	/**
	 * FXML function.
	 * 
	 * Enables elements on the GUI for interface 1.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	public void enableElementsInterface1(ActionEvent event) {

		if (btnEnableInterface1.isSelected()) {// Down
			comboxSpeedInterface1.setDisable(false);
			comboxPortsInterface1.setDisable(false);
			comboxModeInterface1.setDisable(false);
		} else {
			comboxSpeedInterface1.setDisable(true);
			comboxPortsInterface1.setDisable(true);
			comboxModeInterface1.setDisable(true);
		}

	}

	/**
	 * FXML function.
	 * 
	 * Enables elements on the GUI for interface 2.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	public void enableElementsInterface2(ActionEvent event) {
		if (btnEnableInterface2.isSelected()) {// Down
			comboxSpeedInterface2.setDisable(false);
			comboxPortsInterface2.setDisable(false);
			comboxModeInterface2.setDisable(false);
		} else {
			comboxSpeedInterface2.setDisable(true);
			comboxPortsInterface2.setDisable(true);
			comboxModeInterface2.setDisable(true);
		}
	}

	/**
	 * 
	 * FXML Function.
	 * 
	 * Save configuration of the interface 1.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	public void saveInterface1(ActionEvent event) {

		if (btnEnableInterface1.isSelected()) {

			CanUtils.setInterface1State(true);

			/**
			 * Check port.
			 */

			if (comboxPortsInterface1.getSelectionModel().isEmpty()) {
				CanUtils.setInterface1State(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No port selected", "Must select a port to connect.");
			} else if (comboxPortsInterface1.getSelectionModel().getSelectedItem().equals("No ports found")) {

				GuiUtils.generateAlert(AlertType.INFORMATION, "Option not valid", "Selected option is not valid.");

			} else {
				if (inUseCOMPorts.contains(comboxPortsInterface1.getValue())) {
					GuiUtils.generateAlert(AlertType.WARNING, "Port used", "Port used by another interface");
				} else {
					CanUtils.setConfigurationPortInterface1(comboxPortsInterface1.getValue());
					btnSaveInterface1.setStyle(buttonStyle);
					inUseCOMPorts.add(comboxPortsInterface1.getValue());
				}

			}

			/**
			 * Check speed.
			 */
			if (!comboxSpeedInterface1.getSelectionModel().isEmpty()) {
				CanUtils.setConfigurationSpeedInterface1(comboxSpeedInterface1.getValue());
			} else {
				CanUtils.setInterface1State(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No speed selected", "Must select a speed to connect.");
			}

			/**
			 * Check mode.
			 */
			if (!comboxModeInterface1.getSelectionModel().isEmpty()) {
				CanUtils.setConfigurationModeInterface1(comboxModeInterface1.getValue());

			} else {
				CanUtils.setInterface1State(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No mode selected", "Must select a mode to connect.");
			}

		} else {
			// Not enabled
			CanUtils.setInterface1State(false);
			GuiUtils.generateAlert(AlertType.INFORMATION, "Disabled interface", "This interface has been disabled.");
		}

	}

	/**
	 * FXML function.
	 * 
	 * Saves configuration of interface 2.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	public void saveInterface2(ActionEvent event) {

		if (btnEnableInterface2.isSelected()) {

			CanUtils.setInterface2State(true);

			/**
			 * Check port.
			 */

			if (comboxPortsInterface2.getSelectionModel().isEmpty()) {
				CanUtils.setInterface2State(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No port selected", "Must select a port to connect.");
			} else if (comboxPortsInterface2.getSelectionModel().getSelectedItem().equals("No ports found")) {

				GuiUtils.generateAlert(AlertType.INFORMATION, "Option not valid", "Selected option is not valid.");

			} else {
				if (inUseCOMPorts.contains(comboxPortsInterface2.getValue())) {
					GuiUtils.generateAlert(AlertType.WARNING, "Port used", "Port used by another interface");
				} else {
					CanUtils.setConfigurationPortInterface2(comboxPortsInterface2.getValue());
					btnSaveInterface2.setStyle(buttonStyle);
					inUseCOMPorts.add(comboxPortsInterface2.getValue());
				}

			}

			/**
			 * Check speed.
			 */
			if (!comboxSpeedInterface2.getSelectionModel().isEmpty()) {
				CanUtils.setConfigurationSpeedInterface2(comboxSpeedInterface2.getValue());
			} else {
				CanUtils.setInterface2State(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No speed selected", "Must select a speed to connect.");
			}

			/**
			 * Check mode.
			 */
			if (!comboxModeInterface2.getSelectionModel().isEmpty()) {
				CanUtils.setConfigurationModeInterface2(comboxModeInterface1.getValue());

			} else {
				CanUtils.setInterface2State(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No mode selected", "Must select a mode to connect.");
			}

		} else {
			// Not enabled
			CanUtils.setInterface2State(false);
			GuiUtils.generateAlert(AlertType.INFORMATION, "Disabled interface", "This interface has been disabled.");
		}

	}
	/**
	 * FXML function.
	 * 
	 * Save configuration options.
	 * 
	 * @param event Click event of the user.
	 */
	@FXML
	public void saveConfiguration(ActionEvent event) {

		/**
		 * Ignore bytes with value 0.
		 */
		if (checkIgnoreZeroBytes.isSelected()) {
			CanUtils.setIgnoreZeroBytesInterface1(true);
		} else {
			CanUtils.setIgnoreZeroBytesInterface1(false);
		}

		/**
		 * Split bytes.
		 */
		if (checkSplitBytes.isSelected()) {
			CanUtils.setSplitBytesInterface1(true);
		} else {
			CanUtils.setSplitBytesInterface1(false);
		}
		btnSaveConfig.setStyle(buttonStyle);
	}
}
