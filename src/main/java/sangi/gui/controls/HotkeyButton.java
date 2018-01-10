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
        this.setText(keyToString());

        this.setOnAction(event -> {
            if (isRecording){
                isRecording = false;
                setText(keyToString());
            }
            else {
                isRecording = true;
                setText("Recording");
            }
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

    private String keyToString(){
        return key.getName();
    }


}
