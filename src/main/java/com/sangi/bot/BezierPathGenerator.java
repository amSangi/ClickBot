package main.java.com.sangi.bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BezierPathGenerator {

    private static final double CURVE_INCREMENT_VALUE = 0.05;
    private static final double CONTROL_DEGREE = 5;
    private static final double CONTROL_POINT_RAD = (float) Math.toRadians(CONTROL_DEGREE);
    private static final double[] QUADRATIC_CONTROL_POINT_RANGE = {0.25, 0.75};
    private static final double[] CUBIC_CONTROL_POINT_ONE_RANGE = {0.25, 0.45};
    private static final double[] CUBIC_CONTROL_POINT_TWO_RANGE = {0.50, 0.75};

    public List<Point> generateQuadraticBezierCurve(Point start, Point dest){

        double destX = dest.getX();
        double destY = dest.getY();
        double startX = start.getX();
        double startY = start.getY();

        double dx = destX - startX;
        double dy = destY - startY;

        double r = ThreadLocalRandom.current().nextDouble(QUADRATIC_CONTROL_POINT_RANGE[0],
                                                         QUADRATIC_CONTROL_POINT_RANGE[1]);

        // Generate random point on line connecting start and dest
        double randPointX = destX * r;
        double randPointY = destY * r;

        // Generate random control point
        double p1X = randPointX + CONTROL_POINT_RAD * dx;
        double p1Y = randPointY + CONTROL_POINT_RAD * (-dy);

        List<Point> curvePoints = new ArrayList<>();

        // Generate points on the Bezier curve
        for (double t = 0; t <= 1; t += CURVE_INCREMENT_VALUE){
            double dist = 1 - t;
            double x = (dist * ((dist * startX) + (t * p1X))) + (t * ((dist * p1X) + (t * destX)));
            double y = (dist * ((dist * startY) + (t * p1Y))) + (t * ((dist * p1X) + (t * destY)));
            curvePoints.add(new Point((int) x, (int) y));
        }

        return curvePoints;
    }

    public List<Point> generateCubicBezierCurve(Point start, Point dest){
        double destX = dest.getX();
        double destY = dest.getY();
        double startX = start.getX();
        double startY = start.getY();

        double dx = destX - startX;
        double dy = destY - startY;

        double r = ThreadLocalRandom.current().nextDouble(CUBIC_CONTROL_POINT_ONE_RANGE[0],
                                                          CUBIC_CONTROL_POINT_ONE_RANGE[1]);
        double r2 = ThreadLocalRandom.current().nextDouble(CUBIC_CONTROL_POINT_TWO_RANGE[0],
                                                           CUBIC_CONTROL_POINT_TWO_RANGE[1]);


        // Generate random points on line connecting start and dest
        double randPointX = destX * r;
        double randPointY = destY * r;
        double randPointX2 = destX * r2;
        double randPointY2 = destY * r2;

        // Generate random control points
        double p1X = randPointX + CONTROL_POINT_RAD * dx;
        double p1Y = randPointY + CONTROL_POINT_RAD * (-dy);
        // Desire three changes in direction, so we subtract
        double p2X = randPointX2 - CONTROL_POINT_RAD * dx;
        double p2Y = randPointY2 - CONTROL_POINT_RAD * (-dy);

        List<Point> curvePoints = new ArrayList<>();

        // Generate points on the Bezier curve
        for (double t = 0; t <= 1; t += CURVE_INCREMENT_VALUE){
            double dist = 1 - t;
            double tSquared = t * t;
            double x = dist * ((dist * dist * startX) + (3 * dist * t * p1X) + (3 * tSquared * p2X)) +
                                (tSquared * t * destX);
            double y = dist * ((dist * dist * startY) + (3 * dist * t * p1Y) + (3 * tSquared * p2Y)) +
                                (tSquared * t * destY);
            curvePoints.add(new Point((int) x, (int) y));
        }

        return curvePoints;
    }









}
