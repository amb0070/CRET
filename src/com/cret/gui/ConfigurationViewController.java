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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author Adrian Marcos
 *
 */

public class ConfigurationViewController {
	
	
	/**
	 *  ArrayList with the COM ports in use. Used to filter if one port is selected by another interface.
	 */
	
	private List<String> inUseCOMPorts= new ArrayList<>();
	
	
	/**
	 * FXML element.
	 * Accordion used to choose between "Interface 1", "Interface 2" or "Configuration" menus.
	 */
	
	@FXML
	private Accordion accordationConfigure;
	
	
	/**
	 * FXML element.
	 * ComboBox used to show and choose the COM port assigned to interface 1.
	 */
	
	@FXML
	private ComboBox<String> comboxPortsInterface1;
	
	
	/**
	 * FXML element.
	 * ComboBox used to show and choose the COM port assigned to interface 2.
	 */
	
	@FXML
	private ComboBox<String> comboxPortsInterface2;
	
	
	/**
	 * FXML element.
	 * ComboBox used to show and select the connection speed for interface 1.
	 */
	
	@FXML
	private ComboBox<String> comboxSpeedInterface1;
	
	
	/**
	 * FXML element.
	 * ComboBox used to show and select the connection speed for interface 2.
	 */
	
	@FXML
	private ComboBox<String> comboxSpeedInterface2;
	
	
	/**
	 * FXML element.
	 * ComboBox used to show and select the connection mode for interface 1.
	 */
	
	@FXML
	private ComboBox<String> comboxModeInterface1;
	
	
	/**
	 * FXML element.
	 * ComboBox used to show and select the connection mode for interface 2.
	 */
	
	@FXML
	private ComboBox<String> comboxModeInterface2;
	
	
	/**
	 * FXML element.
	 * Button to save data of the interface 1.
	 */
	
	@FXML
	private Button btnSaveInterface1;
	
	
	/**
	 * FXML element.
	 * Button to save data of the interface 2.
	 */
	
	@FXML
	private Button btnSaveInterface2;
	
	
	/**
	 * FXML element.
	 * Button to close configuration window.
	 */
	
	@FXML
	private Button btnCloseInterface1;
	
	
	/**
	 * FXML element.
	 * Button to close configuration window.
	 */
	
	@FXML
	private Button btnCloseInterface2;
	
	
	/**
	 * FXML element.
	 * Button to enable elements to configure interface 1.
	 */
	
	@FXML
	private ToggleButton btnEnableInterface1;
	
	
	/**
	 * FXML element.
	 * Button to enable elements to configure interface 2.
	 */
	
	@FXML
	private ToggleButton btnEnableInterface2;
	
	
	/**
	 * FXML element.
	 * CheckBox to ignore bytes with value 00.
	 */
	
	@FXML
	private CheckBox checkIgnoreZeroBytes;
	
	
	/**
	 * FXML element.
	 * CheckBox to split bytes in 2 parts.
	 */
	
	@FXML
	private CheckBox checkSplitBytes;
	
	
	/**
	 * FXML element.
	 * Button to save data of the configuration pane.
	 */
	
	@FXML
	private Button btnSaveConfig;
	
	
	/**
	 * FXML element.
	 * Button to close configuration window.
	 */
	
	@FXML
	private Button btnCloseConfig;
	
	
	/**
	 * FXML element.
	 * Interface 1 pane from the accordion.
	 */
	
	@FXML
	private TitledPane paneInterface1;
	
	
	/**
	 * FXML function.
	 * When the window is loaded, this function is executed.
	 */
	
	@FXML
	public void initialize() {
		
		//ArrayList to store found COM ports.
		List<String> serialPortList = new ArrayList<String>();
		
		//Call external function to get COM ports and load it on serialPortList.
		Collections.addAll(serialPortList, CanUtils.getSerialPorts());
		
		//ObservableList to load COM ports on the ComboBox elements of the GUI.
		ObservableList<String> obList = FXCollections.observableList(serialPortList);
		
		//Add COM ports to ComboBox menus.
		comboxPortsInterface1.setItems(obList);
		comboxPortsInterface2.setItems(obList);
		
		//ObservableList with pre-configured speeds.
		ObservableList<String> connectionSpeedList = FXCollections.observableArrayList(
			        "10000",
			        "20000",
			        "50000",
			        "100000",
			        "125000",
			        "250000",
			        "500000",
			        "800000",
			        "1000000"
			    );
		
		//ObservableList with the connection available modes.
		ObservableList<String> connectionModes = FXCollections.observableArrayList(
				"ACTIVE",
				"LISTENONLY",
				"LOOPBACK"
				);
		
		//Add speed list to Combobox elements.
		comboxSpeedInterface1.setItems(connectionSpeedList);
		comboxSpeedInterface2.setItems(connectionSpeedList);
		
		//Add mode list to Combobox elements.
		comboxModeInterface1.setItems(connectionModes);
		comboxModeInterface2.setItems(connectionModes);
		
		
		/**
		 * Check when the window is opened, if there is previous configuration for interfaces.
		 */
		
		//If interface 1 is configured:
		if (CanUtils.get1State()) {
			
			//Enable elements of the GUI.
			btnEnableInterface1.setSelected(true);
			comboxSpeedInterface1.setDisable(false);
			comboxPortsInterface1.setDisable(false);
			comboxModeInterface1.setDisable(false);
			
			//Load configuration data into elements of the GUI.
			comboxPortsInterface1.setValue(CanUtils.getConfigPort1());
			comboxSpeedInterface1.setValue(CanUtils.getConfigSpeed1());
			comboxModeInterface1.setValue(CanUtils.getConfigMode1());
		}
		
		//If interface 2 is configured:
		if (CanUtils.get2State()) {
			
			//Enable elements of the GUI.
			btnEnableInterface2.setSelected(true);
			comboxSpeedInterface2.setDisable(false);
			comboxPortsInterface2.setDisable(false);
			comboxModeInterface2.setDisable(false);
			
			//Load configuration data into elements of the GUI.
			comboxPortsInterface2.setValue(CanUtils.getConfigPort2());
			comboxSpeedInterface2.setValue(CanUtils.getConfigSpeed2());
			comboxModeInterface2.setValue(CanUtils.getConfigMode2());
		}
		
		
		//If split bytes option is configured.
		if (CanUtils.getSplitBytes()) {
			checkSplitBytes.setSelected(true);
		} else {
			checkSplitBytes.setSelected(false);
		}
		
		
		//If ignore bytes with value 00 is conigured.
		if (CanUtils.getIgnoreZeroBytes()) {
			checkIgnoreZeroBytes.setSelected(true);
		} else {
			checkIgnoreZeroBytes.setSelected(false);
		}
		
		//Expand first pane of the accordation.
		accordationConfigure.setExpandedPane(paneInterface1);

	}
	
	
	/**
	 * FXML function.
	 * Closes the configuration window.
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
	 * @param event Click event of the user.
	 */
	@FXML
	public void enableElementsInterface1(ActionEvent event) {
		
		if (btnEnableInterface1.isSelected()) {//Down
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
	 * @param event
	 */
	@FXML
	public void enableElementsInterface2(ActionEvent event) {
		if (btnEnableInterface2.isSelected()) {//Down
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
	 * @param event
	 */
	@FXML
	public void saveInterface1(ActionEvent event) {
		

		if (btnEnableInterface1.isSelected()) {
			//Is enabled
			CanUtils.set1(true);
			
			if (!comboxPortsInterface1.getValue().equals("")) {
				if (inUseCOMPorts.contains(comboxPortsInterface1.getValue())) {
					GuiUtils.generateAlert(AlertType.WARNING, "COM Port used", "COM Port used in another interface");
				} else {
				CanUtils.setConfigPort1(comboxPortsInterface1.getValue());
				}
			} else {
				CanUtils.set1(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No port selected", "Must select a port");
			}
			
			if (!comboxSpeedInterface1.getValue().equals("")) {
				CanUtils.setConfigSpeed1(comboxSpeedInterface1.getValue());
			} else {
				CanUtils.set1(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No speed selected", "Must select a speed");
			}
			
			if (!comboxModeInterface1.getValue().equals("")) {
				CanUtils.setConfigMode1(comboxModeInterface1.getValue());
			} else {
				CanUtils.set1(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No mode selected", "Must select a mode");
			}
			
			btnSaveInterface1.setStyle("-fx-base: rgb(0, 255, 0);");
			Image image = new Image("file:resources/tick.png");
			btnSaveInterface1.setGraphic(new ImageView(image));
			inUseCOMPorts.add(comboxPortsInterface1.getValue());
			
		} else {
			//Not enabled
			CanUtils.set1(false);
		}

	}
	
	/**
	 * @param event
	 */
	@FXML
	public void saveInterface2(ActionEvent event) {
		
		//Check if selected port exist
		if (btnEnableInterface2.isSelected()) {
			//Is enabled
			CanUtils.set2(true);
			
			if (!comboxPortsInterface2.getValue().equals("")) {
				if (inUseCOMPorts.contains(comboxPortsInterface2.getValue())) {
					GuiUtils.generateAlert(AlertType.WARNING, "COM Port used", "COM Port used in another interface");
				} else {
					CanUtils.setConfigPort2(comboxPortsInterface2.getValue());
				}
			} else {

				CanUtils.set2(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No port selected", "Must select a port");
			}
			
			if (!comboxSpeedInterface1.getValue().equals("")) {
				CanUtils.setConfigSpeed2(comboxSpeedInterface2.getValue());
			} else {
				CanUtils.set2(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No speed selected", "Must select a speed");
			}
			
			if (!comboxModeInterface2.getValue().equals("")) {
				CanUtils.setConfigMode2(comboxModeInterface2.getValue());
			} else {
				CanUtils.set2(false);
				GuiUtils.generateAlert(AlertType.WARNING, "No mode selected", "Must select a mode");
			}
			
			btnSaveInterface1.setStyle("-fx-base: rgb(0, 255, 0);");
			Image image = new Image("file:resources/tick.png");
			btnSaveInterface2.setGraphic(new ImageView(image));
			inUseCOMPorts.add(comboxPortsInterface2.getValue());
			//GuiUtils.generateAlert(AlertType.INFORMATION, "Success", "Interface has been configured!");
			
		} else {
			//Not enabled
			CanUtils.set2(false);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@FXML
	public void saveConfiguration(ActionEvent event) {
		
		if (checkIgnoreZeroBytes.isSelected()) {
			CanUtils.setIgnoreZeroBytes(true);
		} else {
			CanUtils.setIgnoreZeroBytes(false);
		}
		
		if (checkSplitBytes.isSelected()) {
			CanUtils.setSplitBytes(true);
		} else {
			CanUtils.setSplitBytes(false);
		}	
	}
}
