package com.cret.gui;

import com.cret.staticdata.structures;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Adrian Marcos
 * @version 1.0
 *
 */
class FrameViewerController {

	/**
	 * 
	 */
	@FXML
	private Button btnClose;

	/**
	 * 
	 */
	@FXML
	private Button btnCloseHistory;

	/**
	 * 
	 */
	@FXML
	private Label lblMaxValue;

	/**
	 * 
	 */
	@FXML
	private Label lblID;

	/**
	 * 
	 */
	@FXML
	private Label lblByte;

	/**
	 * 
	 */
	@FXML
	private Label lblValue;

	/**
	 * 
	 */
	@FXML
	private Label lblName;

	/**
	 * 
	 */
	@FXML
	private Gauge gauge;

	String id = "-";
	int byteNumber = 0;
	String valueName = "-";
	private Number data;
	private Timeline timeline2;
	private DoubleProperty value = new SimpleDoubleProperty();

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param byteNumber
	 */
	public void setByteNumber(int byteNumber) {
		this.byteNumber = byteNumber;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.valueName = value;
	}

	/**
	 * @param event
	 */
	@FXML
	private void closeWindow(ActionEvent event) {
		timeline2.stop();
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}


	/**
	 * 
	 */
	private void update() {
		lblID.setText(id);
		lblByte.setText(Integer.toString(byteNumber));

		lblName.setText(valueName);
	}

	/**
	 * 
	 */
	private void update2() {

		if (structures.modeInterface1.equals("Dashboard")) {
			data = structures.xySeriesDash1.get(id).get(byteNumber).getData()
					.get(structures.xySeriesDash1.get(id).get(byteNumber).getData().size() - 1).getYValue();
		} else {
			data = structures.xySeries1.get(id).get(byteNumber).getData()
					.get(structures.xySeries1.get(id).get(byteNumber).getData().size() - 1).getYValue();

		}

		value.setValue(data.intValue());

	}

	/**
	 * @throws InterruptedException
	 */
	@FXML
	private void initialize() throws InterruptedException {

		gauge.valueProperty().bind(value);

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> update()));
		timeline.play();

		gauge.setSkin(new ModernSkin(gauge));
		gauge.setDecimals(0);
		gauge.setBackgroundPaint(Color.BLACK);
		gauge.setValueColor(Color.WHITE);
		gauge.setTitleColor(Color.RED);
		gauge.setSubTitleColor(Color.WHITE);
		gauge.setBarColor(Color.rgb(0, 214, 215));
		gauge.setNeedleColor(Color.rgb(0, 214, 215));
		gauge.setThresholdColor(Color.rgb(204, 0, 0));
		gauge.setTickLabelColor(Color.rgb(151, 151, 151));
		gauge.setTickLabelsVisible(false);
		gauge.setTickMarkColor(Color.BLACK);
		gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
		gauge.setMinValue(0);
		gauge.setMaxValue(260);

		timeline2 = new Timeline(new KeyFrame(Duration.millis(50), ae -> update2()));

		timeline2.setCycleCount(Animation.INDEFINITE);
		timeline2.play();
	}

}
