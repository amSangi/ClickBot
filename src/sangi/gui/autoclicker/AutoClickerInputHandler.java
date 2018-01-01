package sangi.gui.autoclicker;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sangi.bot.AutoClickerBot;
import sangi.gui.KeyHandler;

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
