package com.cret.gui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.hansolo.medusa.Gauge;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FrameViewerController {

	Thread thread;
	ExecutorService executor;
	
	@FXML
	private Button btnClose;
	
	@FXML
	private Button btnCloseHistory;
	
	@FXML
	private Label lblMaxValue;
	
	@FXML
	private Label lblID;
	
	@FXML
	private Label lblByte;
	
	@FXML
	private Label lblValue;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Gauge gauge;
	
	public String id = "-";
	public int byteNumber = 0;

	
	
	public void setId(String id) {
		this.id = id;

	}
	
	public void setByteNumber(int byteNumber) {
		this.byteNumber = byteNumber;
		
	}
	
	@FXML
	private void closeWindow(ActionEvent event) {
	    Stage stage = (Stage) btnClose.getScene().getWindow();
	    stage.close();
	}
	
	 Task <Void> task = new Task<Void>() {
	      @Override public Void call() throws InterruptedException {
	        // "message2" time consuming method (this message will be seen).
	    	  System.out.println("DENTRO");
	    	  

	    	  while (true) {
	    		  
	    		  Number data = RootViewController.xySeries.get(id).get(byteNumber).getData().get(RootViewController.xySeries.get(id).get(byteNumber).getData().size()-1).getYValue();
	    		  String last = data.toString();
	    		  updateMessage(last);
	    		  if (data.intValue() > 0) {
	    			  
	    		  }
	    		  updateProgress(data.doubleValue(), 100.0);
	    		 
	    		  Thread.sleep(100);
	    	  }
	        // this will never be actually be seen because we also set a message 
	        // in the task::setOnSucceeded handler.
	      }
	    };
	    
	private void update() {
		lblID.setText(id);
		lblByte.setText(Integer.toString(byteNumber));
	}
	
	private void update2() {
		gauge.setValue(progressBar.getProgress()*100);
		//System.out.println(progressBar.getProgress());
	}
	
	@FXML
	private void initialize() throws InterruptedException {
		
			    lblValue.textProperty().bind(task.messageProperty());
			    progressBar.progressProperty().bind(task.progressProperty());
			    executor = Executors.newSingleThreadExecutor();
		        executor.submit(task);
		       // gauge.setAngleRange(10.0);
		        //gauge.setAngleRange(90.0);
		       // gauge.valueProperty().bind(task.progressProperty());
		        
		        Timeline timeline = new Timeline(new KeyFrame(
		                Duration.millis(1000),
		                ae -> update()));
		        timeline.play();
		        
		    	Timeline timeline2 = new Timeline(new KeyFrame(
		    	        Duration.millis(100),
		    	        ae -> update2()));
		    	timeline2.setCycleCount(Animation.INDEFINITE);
		    	timeline2.play();

	}


}
