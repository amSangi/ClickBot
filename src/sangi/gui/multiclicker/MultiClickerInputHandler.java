package sangi.gui.multiclicker;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sangi.bot.MultiClickerBot;
import sangi.gui.KeyHandler;

import java.awt.*;
import java.util.List;


public class MultiClickerInputHandler implements KeyHandler{
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
            List<Point> points = controller.getPoints();
            int ppSecondDelay = controller.getPPSeconds();
            int ppMillisDelay = controller.getPPMillis();
            int clickSecondDelay = controller.getClickSeconds();
            int clickMillisDelay = controller.getClickMillis();
            boolean isLinearTraversal = controller.isLinear();

            int totalPPDelay = (1000 * ppSecondDelay) + ppMillisDelay;
            int totalClickDelay = (1000 * clickSecondDelay) + clickMillisDelay;

            multiClickerBot.beginMultiClick(points, totalPPDelay, totalClickDelay, isLinearTraversal);

            System.out.println("Starting multiclick");
            System.out.println(points.toString());
            System.out.println("Point to point delay: " + totalPPDelay);
            System.out.println("Click delay: " + totalClickDelay);
            System.out.println("Linear Traversal: " + isLinearTraversal);
        }
        else if (stopKeyPressed){
            multiClickerBot.stopMultiClick();
            System.out.println("Stopping multiclick");
        }

    }

    public void handleKeyReleased(KeyEvent keyEvent){
        KeyCode keyCode = keyEvent.getCode();

        boolean saveKeyPressed = keyCode.equals(controller.getSavePointKey());
        boolean deleteKeyPressed = keyCode.equals(controller.getDeleteLastPointKey());

        if (saveKeyPressed){
            controller.addNewPoint(currentMousePosition);
            System.out.println("Saving point: " + currentMousePosition.toString());
        }
        else if (deleteKeyPressed){
            controller.removeLastPoint();
            System.out.println("Deleting last point");
        }
    }

    public void setCurrentMousePosition(Point newPoint){
        currentMousePosition = newPoint;
    }

}
