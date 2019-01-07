package com.cret.main;
	
import java.io.IOException;

import com.cret.interfaces.CANCon;
import com.cret.interfaces.CanUtils;

import de.fischl.usbtin.USBtinException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	

	@Override
	public void start(Stage stage) throws Exception {
		//Set window title
        stage.setTitle("CRET - CanBus Reverse Engineering Toolkit");
        //Load the FXML document on the interface.
        Pane root = (Pane)FXMLLoader.load(getClass().getResource("/com/cret/gui/main.fxml"));
        //Create scene and load pane into.
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        stage.setScene(scene);
        //Show the window maximized
        stage.setMaximized(true);
        stage.show();
        
	}

	public static void main(String[]args) throws IOException, USBtinException{
		//(CANCon can = new CANCon();
		//can.start();

		launch(args);
		
	}
	
}
