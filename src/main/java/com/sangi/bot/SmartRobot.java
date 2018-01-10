package main.java.com.sangi.bot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.ThreadLocalRandom;

public class SmartRobot extends Robot {

    // Click Constants
    private static final int MOUSE_BUTTON = InputEvent.BUTTON1_DOWN_MASK;
    private static final int RELEASE_DELAY = 100;
    private static final int[] RELEASE_RANDOMIZATION_RANGE = {5, 105};
    private static final int[] PRESS_RANDOMIZATION_RANGE = {10, 350};

    // Linear Move Constants
    private static final int POSITION_VARIANCE = 2;
    private static final int MOUSE_MOVEMENT_STEPS = 100;

    // Human-Like Move Constants
    private static final double OVERSHOOT_DELAY_FACTOR = 0.75;
    private static final double NEW_DEST_DELAY_FACTOR = 1 - OVERSHOOT_DELAY_FACTOR;
    private static final double[] OVERSHOOT_FACTOR_RANGE = {1.0, 1.05};

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
     *      - Overshooting of points
     *      - Non-linear movements
     */
    public void humanMoveTowards(Point dest, int delay){
        Point start = MouseInfo.getPointerInfo().getLocation();

        // New destination
        Point newDest = generatePositionalVariant(dest);

        // Overshoot position
        Point overshootPosition = generateOvershootPosition(start, dest);

        // Generate paths to each point
        java.util.List<Point> pointsToOvershoot = pathGenerator.generateCubicBezierCurve(start, overshootPosition);
        java.util.List<Point> pointsToNewDest = pathGenerator.generateQuadraticBezierCurve(overshootPosition, newDest);


        int overshootPathSize = pointsToOvershoot.size();
        int newDestPathSize= pointsToNewDest.size();

        int totalPoints = overshootPathSize + newDestPathSize;

        // Movement Delay
        double overshootDelay = delay / (OVERSHOOT_DELAY_FACTOR * totalPoints);
        double newDestDelay = delay / (NEW_DEST_DELAY_FACTOR * totalPoints);


        for (Point p : pointsToOvershoot) {
            this.mouseMove(p.x, p.y);
            try {
                Thread.sleep((long) overshootDelay);
            }
            catch (InterruptedException e){
                System.out.println("Failed to wait before next move - overshoot");
            }
        }

        for (Point p : pointsToNewDest) {
            this.mouseMove(p.x, p.y);
            try {
                Thread.sleep((long) newDestDelay);
            }
            catch (InterruptedException e){
                System.out.println("Failed to wait before next move - overshoot correction");
            }
        }

    }

    private Point generatePositionalVariant(Point p){
        int newX = p.x + ThreadLocalRandom.current().nextInt(-POSITION_VARIANCE, POSITION_VARIANCE);
        int newY = p.y + ThreadLocalRandom.current().nextInt(-POSITION_VARIANCE, POSITION_VARIANCE);
        return new Point(newX, newY);
    }

    private Point generateOvershootPosition(Point start, Point dest){

        double startX = start.getX();
        double startY = start.getY();

        double dx = dest.getX() - startX;
        double dy = dest.getY() - startY;

        // Random Overshoot percentage
        double newX = startX + dx * ThreadLocalRandom.current().nextDouble(OVERSHOOT_FACTOR_RANGE[0],
                                                                           OVERSHOOT_FACTOR_RANGE[1]);
        double newY = startY + dy *  ThreadLocalRandom.current().nextDouble(OVERSHOOT_FACTOR_RANGE[0],
                                                                            OVERSHOOT_FACTOR_RANGE[1]);

        return new Point((int) Math.round(newX), (int) Math.round(newY));
    }

}
