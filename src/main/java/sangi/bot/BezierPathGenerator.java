package main.java.sangi.bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BezierPathGenerator {

    // Path Smoothness Constants
    private static final float CURVE_INCREMENT_VALUE = 0.01f;

    // Slope Constants
    private static final float CONTROL_DEGREE = 15f;
    private static final float CONTROL_POINT_RAD = (float) Math.toRadians(CONTROL_DEGREE);

    // Random Control Point Locations
    private static final double[] CONTROL_POINT_ONE_RANGE = {0.25, 0.45};
    private static final double[] CONTROL_POINT_TWO_RANGE = {0.50, 0.75};


    public List<Point> generateCubicBezierCurve(Point start, Point dest){
        int destX = dest.x;
        int destY = dest.y;
        int startX = start.x;
        int startY = start.y;

        int dx = destX - startX;
        int dy = destY - startY;

        float r = (float) ThreadLocalRandom.current().nextDouble(CONTROL_POINT_ONE_RANGE[0],
                                                                 CONTROL_POINT_ONE_RANGE[1]);
        float r2 = (float) ThreadLocalRandom.current().nextDouble(CONTROL_POINT_TWO_RANGE[0],
                                                                  CONTROL_POINT_TWO_RANGE[1]);

        // Generate random points on line connecting endpoints
        float randPointX = startX + dx * r;
        float randPointY = startY + dy * r;
        float randPointX2 = startX + dx * r2;
        float randPointY2 = startY + dy * r2;

        // Used to ensure control points are not always on the same side of endpoints
        int randSign = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;

        // Generate random control points
        float p1X = randPointX + (randSign * -1 * CONTROL_POINT_RAD * (-dy));
        float p1Y = randPointY + (randSign * -1 * CONTROL_POINT_RAD * dx);
        float p2X = randPointX2 + (randSign * CONTROL_POINT_RAD * (-dy));
        float p2Y = randPointY2 + (randSign * CONTROL_POINT_RAD * dx);

        List<Point> curvePoints = new ArrayList<>();

        // Generate points on the Bezier curve
        for (float t = 0; t <= 1; t += CURVE_INCREMENT_VALUE){
            float dist = 1 - t;
            float tSquared = t * t;

            float x = dist * ((dist * dist * startX) + (3 * dist * t * p1X) + (3 * tSquared * p2X)) +
                    (tSquared * t * destX);
            float y = dist * ((dist * dist * startY) + (3 * dist * t * p1Y) + (3 * tSquared * p2Y)) +
                    (tSquared * t * destY);

            curvePoints.add(new Point(Math.round(x), Math.round(y)));
        }
        curvePoints.add(dest);

        return curvePoints;
    }
}
