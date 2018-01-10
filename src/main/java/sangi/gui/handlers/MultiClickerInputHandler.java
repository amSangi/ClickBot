package main.java.sangi.gui.handlers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.sangi.bot.MultiClickerBot;
import main.java.sangi.gui.KeyHandler;
import main.java.sangi.gui.controllers.MultiClickerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MultiClickerInputHandler implements KeyHandler {
    private MultiClickerController controller;
    private MultiClickerBot multiClickerBot;
    private Point currentMousePosition;

    public MultiClickerInputHandler(MultiClickerController multiClickerController) throws AWTException{
        controller = multiClickerController;
        multiClickerBot = new MultiClickerBot();
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();

        boolean isRecordingStartKey = controller.isStartButtonRecording();
        boolean isRecordingStopKey = controller.isStopButtonRecording();
        boolean isRecordingSaveKey = controller.isSaveButtonRecording();
        boolean isRecordingDeleteKey = controller.isDeleteButtonRecording();
        boolean startKeyPressed = keyCode.equals(controller.getStartKey());
        boolean stopKeyPressed = keyCode.equals(controller.getStopKey());

        // Handle Hotkey Changes
        if (isRecordingStartKey){
            controller.setStartKey(keyCode);
            return;
        }
        else if (isRecordingStopKey){
            controller.setStopKey(keyCode);
            return;
        }
        else if (isRecordingSaveKey){
            controller.setSavePointKey(keyCode);
            return;
        }
        else if (isRecordingDeleteKey){
            controller.setDeleteLastPointKey(keyCode);
            return;
        }

        // Handle Hotkey Presses
        if (startKeyPressed){
            List<Point> points = new ArrayList<Point>(controller.getPoints());
            int ppSecondDelay = controller.getPPSeconds();
            int ppMillisDelay = controller.getPPMillis();
            int clickSecondDelay = controller.getClickSeconds();
            int clickMillisDelay = controller.getClickMillis();
            boolean isLinearTraversal = controller.isLinear();

            int totalPPDelay = (1000 * ppSecondDelay) + ppMillisDelay;
            int totalClickDelay = (1000 * clickSecondDelay) + clickMillisDelay;

            multiClickerBot.beginMultiClick(points, totalPPDelay, totalClickDelay, isLinearTraversal);
        }
        else if (stopKeyPressed){
            multiClickerBot.stopMultiClick();
        }

    }

    public void handleKeyReleased(KeyEvent keyEvent){
        KeyCode keyCode = keyEvent.getCode();

        boolean saveKeyPressed = keyCode.equals(controller.getSavePointKey());
        boolean deleteKeyPressed = keyCode.equals(controller.getDeleteLastPointKey());

        if (saveKeyPressed){
            controller.addNewPoint(currentMousePosition);
        }
        else if (deleteKeyPressed){
            controller.removeLastPoint();
        }
    }

    public void setCurrentMousePosition(Point newPoint){
        currentMousePosition = newPoint;
    }

}
