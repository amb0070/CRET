package com.cret.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cret.interfaces.CanUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class ConfigController {
	
	String selectedPort = "";
	
	@FXML
	private ComboBox<String> comboPorts1;
	
	@FXML
	private ComboBox<String> comboPorts2;
	
	@FXML
	private ComboBox<String> speed1;
	
	@FXML
	private ComboBox<String> speed2;
	
	@FXML
	private Button btnSave1;
	
	@FXML
	private Button btnSave2;
	
	@FXML
	private Button btnClose1;
	
	@FXML
	private Button btnClose2;
	
	@FXML
	private ToggleButton btnEnable1;
	
	@FXML
	private ToggleButton btnEnable2;
	
	
	
	
	@FXML
	public void initialize() {
		
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, CanUtils.getSerialPorts());
		ObservableList<String> obList = FXCollections.observableList(list);
		comboPorts1.setItems(obList);
		comboPorts2.setItems(obList);
		
		
		ObservableList<String> speedList = FXCollections.observableArrayList(
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
		
		speed1.setItems(speedList);
		speed2.setItems(speedList);
		

	}
	
	@FXML
	public void closeWindow(ActionEvent event) {
	    Stage stage = (Stage) btnClose1.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void enableInterface1(ActionEvent event) {
		
		if (btnEnable1.isSelected()) {//Down
			speed1.setDisable(false);
			comboPorts1.setDisable(false);
		} else {
			speed1.setDisable(true);
			comboPorts1.setDisable(true);
		}
		
	}
	
	@FXML
	public void enableInterface2(ActionEvent event) {
		if (btnEnable2.isSelected()) {//Down
			speed2.setDisable(false);
			comboPorts2.setDisable(false);
		} else {
			speed2.setDisable(true);
			comboPorts2.setDisable(true);
		}
	}
	
	@FXML
	public void saveInterface1(ActionEvent event) {
		
		
		//Check if selected port exist
		if (btnEnable1.isSelected()) {
			//Is enabled
			CanUtils.set1(true);
			CanUtils.setConfigPort1(comboPorts1.getValue());
			CanUtils.setConfigSpeed1(speed1.getValue());
		} else {
			//Not enabled
			CanUtils.set1(false);
		}

	}
	
	@FXML
	public void saveInterface2(ActionEvent event) {
		
		//Check if selected port exists
		if (btnEnable2.isSelected()) {
			//Is enabled
			CanUtils.set2(true);
			CanUtils.setConfigPort1(comboPorts2.getValue());
			CanUtils.setConfigSpeed1(speed2.getValue());
			
			
		} else {
			//Not enabled
			CanUtils.set2(false);
		}
	}
	
	

}
