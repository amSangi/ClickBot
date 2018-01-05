package main.java.com.sangi.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import main.java.com.sangi.gui.controls.HotkeyButton;
import main.java.com.sangi.gui.controls.IntegerSpinner;

public class AutoClickerController {


    // FXML Controls
    @FXML private IntegerSpinner secondsDelaySpinner;
    @FXML private IntegerSpinner millisDelaySpinner;
    @FXML private IntegerSpinner clickCountSpinner;
    @FXML private ToggleGroup countGroup;
    @FXML private Toggle manualStopToggle;
    @FXML private CheckBox clickRandomizationCheckBox;
    @FXML private HotkeyButton startKeyButton;
    @FXML private HotkeyButton stopKeyButton;



    /******  Button Methods *****/

    public void setStartKey(KeyCode newKey){
        if (!newKey.equals(stopKeyButton.getKey())) {
            startKeyButton.setKey(newKey);
        }
    }

    public void setStopKey(KeyCode newKey){
        if (!newKey.equals(startKeyButton.getKey())){
            stopKeyButton.setKey(newKey);
        }
    }

    public KeyCode getStartKey(){
        return startKeyButton.getKey();
    }

    public KeyCode getStopKey(){
        return stopKeyButton.getKey();
    }

    public boolean isStartButtonRecording(){
        return startKeyButton.isRecording();
    }

    public boolean isStopButtonRecording(){
        return stopKeyButton.isRecording();
    }

    /******  Count Methods *****/

    public int getClickCount(){
        return clickCountSpinner.getValue();
    }

    public boolean isManualStop(){
        return countGroup.getSelectedToggle().equals(manualStopToggle);
    }


    /******  Delay Methods *****/

    public int getSeconds(){
        return secondsDelaySpinner.getValue();
    }

    public int getMillis(){
        return millisDelaySpinner.getValue();
    }

    /******  Randomization Methods *****/

    public boolean isRandomized(){
        return clickRandomizationCheckBox.isSelected();
    }
}
