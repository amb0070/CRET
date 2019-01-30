package com.cret.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * GUI Utilities accessible from every class.
 * 
 * Generates alert messages and builds icon images.
 * 
 * @author Adrian Marcos
 * @version 1.0
 *
 */
public class GuiUtils {

	
	/**
	 * Generates alert dialog to inform to the user.
	 * @param type
	 * @param title
	 * @param msg
	 */
	public static void generateAlert(AlertType type, String title, String msg) {
		
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(msg);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:resources/icon.png"));
		//alert.setContentText("I have a great message for you!");
		alert.showAndWait().ifPresent(rs -> {
		});
	}
	
	
    /**
     * Returns ImageView object taking it from a path of the system.
     * @param imgPatch
     * @return
     */
    public static ImageView buildImage(String imgPatch) {
        Image i = new Image(imgPatch);
        ImageView imageView = new ImageView();
        //You can set width and height
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }
}
