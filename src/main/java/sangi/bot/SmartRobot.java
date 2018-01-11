package main.java.sangi.bot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.ThreadLocalRandom;

public class SmartRobot extends Robot {

    // Click Constants
    private static final int MOUSE_BUTTON = InputEvent.BUTTON1_DOWN_MASK;
    private static final int RELEASE_DELAY = 100;
    private static final int[] RELEASE_RANDOMIZATION_RANGE = {5, 105};
    private static final int[] PRESS_RANDOMIZATION_RANGE = {10, 350};

    private static final int POSITION_VARIANCE = 3;
    private static final int MOUSE_MOVEMENT_STEPS = 100;

    private final BezierPathGenerator pathGenerator = new BezierPathGenerator();

    public SmartRobot() throws AWTException{
        super();
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

    public void linearMoveTowards(Point dest, int delay){
        Point start = MouseInfo.getPointerInfo().getLocation();

        double dx = (dest.x - start.x) / MOUSE_MOVEMENT_STEPS;
        double dy = (dest.y - start.y) / MOUSE_MOVEMENT_STEPS;
        double dt = delay / MOUSE_MOVEMENT_STEPS;
        for (int step = 1; step <= MOUSE_MOVEMENT_STEPS; step++){
            this.mouseMove((int) (start.x + dx * step), (int) (start.y + dy * step));
            try {
                Thread.sleep((long) dt);
            }
            catch (InterruptedException e){
                System.out.println("Failed to wait before next mouse move");
            }
        }
    }

    /**
     *  Features of human-like mouse movements:
     *      - Small position variance (i.e. final resting point is slightly different each time)
     *      - Non-linear movements
     */
    public void humanMoveTowards(Point dest, int delay){
        Point start = MouseInfo.getPointerInfo().getLocation();
        Point newDest = generatePointVariant(dest);

        java.util.List<Point> path = pathGenerator.generateCubicBezierCurve(start, newDest);

        double delayPerPoint = delay / path.size();

        for (Point p : path){
            this.mouseMove(p.x, p.y);
            try {
                Thread.sleep((long) delayPerPoint);
            }
            catch (InterruptedException e){
                System.out.println("Failed to wait before next move");
            }
        }
    }

    private Point generatePointVariant(Point p){
        int newX = p.x + ThreadLocalRandom.current().nextInt(-POSITION_VARIANCE, POSITION_VARIANCE);
        int newY = p.y + ThreadLocalRandom.current().nextInt(-POSITION_VARIANCE, POSITION_VARIANCE);
        return new Point(newX, newY);
    }


}
