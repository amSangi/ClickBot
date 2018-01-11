package main.java.sangi.gui.controls;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;


public class HotkeyButton extends Button {

    private KeyCode key;
    private boolean isRecording = false;

    public HotkeyButton(@NamedArg("defaultKey") KeyCode defaultKey){
        super();
        this.key = defaultKey;
        this.setText(key.getName());

        this.setOnAction(event -> {
            if (isRecording){
                isRecording = false;
                setText(key.getName());
            }
            else {
                isRecording = true;
                setText("Recording");
            }
        });

        this.setOnMouseEntered(event -> {
            if (!isRecording) { setText("Click to edit"); }
        });

        this.setOnMouseExited(event -> {
            if (!isRecording) { setText(key.getName()); }
        });

    }

    public KeyCode getKey(){
        return key;
    }

    public void setKey(KeyCode newKey){
        key = newKey;
    }

    public boolean isRecording(){
        return isRecording;
    }



}
