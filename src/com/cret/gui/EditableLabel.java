package com.cret.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class EditableLabel extends Label{
    TextField tf = new TextField();
    /***
     * backup is used to cancel when press ESC...
     */
    String backup = "";
    public EditableLabel(){
        this("");
    }
    public EditableLabel(String str){
        super(str);
        this.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){
                tf.setText(backup = this.getText());
                this.setGraphic(tf);
                this.setText("");
                tf.requestFocus();
            }
        });
        tf.focusedProperty().addListener((prop, o, n) -> {
            if(!n){
                toLabel();
            }
        });
        tf.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                toLabel();
            }else if(e.getCode().equals(KeyCode.ESCAPE)){
                tf.setText(backup);
                toLabel();
            }
        });
    }

    void toLabel(){
        this.setGraphic(null);
        this.setText(tf.getText());
    }

}