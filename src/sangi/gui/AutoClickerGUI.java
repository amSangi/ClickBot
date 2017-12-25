package sangi.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;

import java.awt.event.ActionEvent;

public class AutoClickerGUI {

    // Constants
    private static final KeyCode DEFAULT_START_KEY = KeyCode.F1;
    private static final KeyCode DEFAULT_STOP_KEY = KeyCode.F2;
    private static final float DEFAULT_SECOND_DELAY = 0f;
    private static final float DEFAULT_MS_DELAY = 100f;


    // Delay
    private float seconds = DEFAULT_SECOND_DELAY;
    private float milliseconds = DEFAULT_MS_DELAY;

    // Count
    private boolean manualStop = true;
    private int clickCount = 0;

    // Hotkeys
    private KeyCode startKey = DEFAULT_START_KEY;
    private KeyCode stopKey = DEFAULT_STOP_KEY;
    private boolean recordStartKey = false;
    private boolean recordStopKey = false;

    // Controls
    @FXML
    public Spinner<Float> secondsDelaySpinner;
    @FXML
    public Spinner<Float> millisecondsDelaySpinner;
    @FXML
    public Spinner<Integer> clickCountSpinner;
    @FXML
    public ToggleGroup countGroup;
    @FXML
    public Button startKeyButton;
    @FXML
    public Button stopKeyButton;

    public AutoClickerGUI(){


    }



    /******  Button Methods *****/

    public void recordNewStartKey(){
        if (!recordStartKey){
            recordStartKey = true;
            startKeyButton.setText("Recording");
            resetStopButton();
            return;
        }

        startKeyButton.setText(startKey.toString());
        recordStartKey = false;
    }

    public void recordNewStopKey(){
        if (!recordStopKey){
            recordStopKey = true;
            stopKeyButton.setText("Recording");
            resetStartButton();
            return;
        }

        stopKeyButton.setText(stopKey.toString());
        recordStopKey = false;

    }

    public void resetStartButton(){
        startKeyButton.setText(startKey.toString());
        recordStartKey = false;
    }

    public void resetStopButton(){
        stopKeyButton.setText((stopKey.toString()));
        recordStopKey = false;
    }

    public void setStartKey(KeyCode newKey){
        startKey = newKey;
    }

    public void setStopKey(KeyCode newKey){
        stopKey = newKey;
    }

    public KeyCode getStartKey(){
        return startKey;
    }

    public KeyCode getStopKey(){
        return stopKey;
    }

    public void test(ActionEvent e){
        System.out.println(e.toString());
    }
    /******  Count Methods *****/

    public void setManualStop(){
        manualStop = true;
    }

    public void setClickCount(){
        manualStop = false;
    }

    public int getClickCount(){
        return clickCountSpinner.getValue();
    }

    public boolean isManualStop(){
        return manualStop;
    }


    /******  Delay Methods *****/

    public float getSeconds(){
        return secondsDelaySpinner.getValue();
    }

    public float getMilliseconds(){
        return millisecondsDelaySpinner.getValue();
    }

}
