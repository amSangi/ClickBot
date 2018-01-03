package sangi.bot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.ThreadLocalRandom;

public class SmartRobot extends Robot {

    // Constants
    private static final int MOUSE_BUTTON = InputEvent.BUTTON1_DOWN_MASK;
    private static final int RELEASE_DELAY = 100;
    private static final int[] RELEASE_RANDOMIZATION_RANGE = {5, 105};
    private static final int[] PRESS_RANDOMIZATION_RANGE = {10, 350};

    // Math Constants
    private static final double MIN_DISTANCE = 1;
    private static final double SPEED = 10;


    public SmartRobot() throws AWTException{
        super();
    }

    // TODO: Finish impl
    public void linearMoveTowards(Point dest, int delay){
        System.out.println("Linear move towards called");

        Point currentPosition = MouseInfo.getPointerInfo().getLocation();
        double distance = Math.hypot(dest.x - currentPosition.x, dest.y - currentPosition.y);
        


    }

    // TODO: Finish impl
    public void humanMoveTowards(Point dest, int delay){
        System.out.println("Human move towards called");
    }

    public void mouseClick(int pressDelay){
        this.mousePress(MOUSE_BUTTON);

        try {
            Thread.sleep(Integer.toUnsignedLong(RELEASE_DELAY));
        }
        catch (InterruptedException e){
            System.out.println("Release delay failed");
        }

        this.mouseRelease(MOUSE_BUTTON);

        try {
            Thread.sleep(Integer.toUnsignedLong(pressDelay));
        }
        catch (InterruptedException e){
            System.out.println("Press delay failed");
        }

    }

    public void randomMouseClick(int pressDelay){

        this.mousePress(MOUSE_BUTTON);

        try {
            int delay = RELEASE_DELAY;
            delay += ThreadLocalRandom.current().nextInt(RELEASE_RANDOMIZATION_RANGE[0],
                                                         RELEASE_RANDOMIZATION_RANGE[1]);
            Thread.sleep(Integer.toUnsignedLong(delay));
        }
        catch (InterruptedException e){
            System.out.println("Release delay failed");
        }

        this.mouseRelease(MOUSE_BUTTON);

        try {
            int delay = pressDelay;
            delay += ThreadLocalRandom.current().nextInt(PRESS_RANDOMIZATION_RANGE[0],
                                                         PRESS_RANDOMIZATION_RANGE[1]);
            Thread.sleep(Integer.toUnsignedLong(delay));
        }
        catch (InterruptedException e){
            System.out.println("Press delay failed");
        }

    }




}
