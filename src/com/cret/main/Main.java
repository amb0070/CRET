package com.cret.main;
	
import com.cret.interfaces.CANCon;
import com.cret.interfaces.Utils;

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
        stage.setScene(scene);
        //Show the window maximized
        stage.setMaximized(true);
        stage.show();
	}

	public static void main(String[]args){
		launch(args);
	}
	
	
}
