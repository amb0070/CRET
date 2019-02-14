package com.cret.main;

import java.io.IOException;
import com.sun.javafx.application.LauncherImpl;
import de.fischl.usbtin.USBtinException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Main class. It starts JavaFX windows.
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// Set window title
		stage.setTitle("CRET - CanBus Reverse Engineering Toolkit");
		// Set icon
		stage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/icon.png")));
		// Load the FXML document on the interface.
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("/com/cret/gui/RootView.fxml"));
		// Create scene and load pane into.
		Scene scene = new Scene(root);
		// Set CSS file.
		
		scene.getStylesheets().add(Thread.currentThread().getContextClassLoader().getResource("resources/main.css").toExternalForm());
		stage.setScene(scene);
		// Show the window maximized
		stage.setMaximized(true);
		stage.show();
		stage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(1);
		});

	}


	/**
	 * Starts 
	 * @param args
	 * @throws IOException
	 * @throws USBtinException
	 */
	public static void main(String[] args) throws IOException, USBtinException {
		LauncherImpl.launchApplication(Main.class, preloader.class, args);
	}
}
