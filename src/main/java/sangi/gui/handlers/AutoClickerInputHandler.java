package main.java.sangi.gui.handlers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.sangi.bot.AutoClickerBot;
import main.java.sangi.gui.KeyHandler;
import main.java.sangi.gui.controllers.AutoClickerController;

import java.awt.*;

public class AutoClickerInputHandler implements KeyHandler{
    private AutoClickerController controller;
    private AutoClickerBot autoClickerBot;

    public AutoClickerInputHandler(AutoClickerController autoClickerController) throws AWTException{
        controller = autoClickerController;
        autoClickerBot = new AutoClickerBot();
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();

        boolean isRecordingStartKey = controller.isStartButtonRecording();
        boolean isRecordingStopKey = controller.isStopButtonRecording();
        boolean startKeyPressed = keyCode.equals(controller.getStartKey());
        boolean stopKeyPressed = keyCode.equals(controller.getStopKey());

        if (isRecordingStartKey){
            controller.setStartKey(keyCode);
            return;
        }
        else if (isRecordingStopKey){
            controller.setStopKey(keyCode);
            return;
        }

        if (startKeyPressed){
            boolean isManualStop = controller.isManualStop();
            boolean isRandomized = controller.isRandomized();

            int clickCount = controller.getClickCount();

            int secondDelay = controller.getSeconds();
            int millisecondDelay = controller.getMillis();
            int totalMillisDelay = (1000 * secondDelay) + millisecondDelay;

            autoClickerBot.beginAutoClick(totalMillisDelay, clickCount, isManualStop, isRandomized);
        }
        else if (stopKeyPressed){
            autoClickerBot.stopAutoClick();
        }
    }

}
