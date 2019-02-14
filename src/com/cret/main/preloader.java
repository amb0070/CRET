package com.cret.main;

import java.io.IOException;
 import com.cret.gui.GuiUtils;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * Application preloader. Its shown while app loads.
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class preloader extends Preloader {

	private Stage stage;

	@FXML
	private ImageView imageViewPreloader;
	
	/**
	 * Creates the scene.
	 * @return
	 */
	private Scene createPreloaderScene() {
		Pane root = null;
		try {
			root = (Pane) FXMLLoader.load(getClass().getResource("/com/cret/main/preloader.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GuiUtils.generateAlert(AlertType.ERROR, "PRELOADER ERROR", "Error loading preloader.");
		}
		// Create scene and load pane into.
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		return scene;
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/icon.png")));
		stage.setScene(createPreloaderScene());
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();

	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification scn) {
		if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
			stage.hide();
		}
	}
	
	@FXML
	private void initialize() {
		imageViewPreloader.setImage(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/LOGO.png")));
	}

}
