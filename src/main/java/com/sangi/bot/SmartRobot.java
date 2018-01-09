package main.java.com.sangi.bot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.ThreadLocalRandom;

public class SmartRobot extends Robot {

    // Constants
    private static final int MOUSE_BUTTON = InputEvent.BUTTON1_DOWN_MASK;
    private static final int RELEASE_DELAY = 100;
    private static final int[] RELEASE_RANDOMIZATION_RANGE = {5, 105};
    private static final int[] PRESS_RANDOMIZATION_RANGE = {10, 350};

    private static final int MOUSE_MOVEMENT_STEPS = 100;
    private static final int OVERSHOOT_CORRECTION_STEPS = 25;
    private static final int POSITION_VARIANCE = 2;
    private static final double[] OVERSHOOT_FACTOR_RANGE = {1.05, 1.15};

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
            try {
                Thread.sleep((long) dt);
            }
            catch (InterruptedException e){
                System.out.println("Failed to wait before next mouse move");
            }
            this.mouseMove((int) (start.x + dx * step), (int) (start.y + dy * step));
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

        // Very Small Final Position Variance
        Point newDest = generatePositionalVariant(dest);
        System.out.println("New Destination: " + newDest.toString());
        // Overshoot Position
        Point overshootPosition = generateOvershootPosition(start, dest);
        System.out.println("Overshoot position: " + overshootPosition.toString());

        // Velocity
        final int totalSteps = MOUSE_MOVEMENT_STEPS + OVERSHOOT_CORRECTION_STEPS;
        double dx = (dest.x - start.x) / totalSteps;
        double dy = (dest.y - start.y) / totalSteps;
        double dt = delay / totalSteps;

        java.util.List<Point> pointsToOverShoot = pathGenerator.generateCubicBezierCurve(start, overshootPosition);
        java.util.List<Point> pointsToNewDest = pathGenerator.generateQuadraticBezierCurve(overshootPosition, newDest);

        // TODO: Finish impl
        

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
