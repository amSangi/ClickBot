package sangi.bot;

import java.awt.*;
import java.util.List;

public class MultiClickerBot {

    // Constants
    private static final int DEFAULT_PP_MILLIS_DELAY = 100;
    private static final int DEFAULT_CLICK_MILLIS_DELAY = 100;

    // Member Variables
    private Thread botThread;
    private MultiClickerRunnable multiClickerRunnable;
    private List<Point> points;
    private int ppDelay = DEFAULT_PP_MILLIS_DELAY;
    private int clickDelay = DEFAULT_CLICK_MILLIS_DELAY;
    private boolean isLinear = false;
    private volatile boolean isRunning = false;
    private final Object runningLock = new Object();

    private class MultiClickerRunnable implements Runnable{
        private SmartRobot robot;

        private MultiClickerRunnable() throws AWTException{
            robot = new SmartRobot();
        }

        @Override
        public void run() {
            if (isLinear){
                startLinearClicks();
            }
            else {
                startHumanLikeClicks();
            }
        }

        // TODO: Finish impl
        private void startLinearClicks(){
            // stub
        }

        // TODO: Finish Implement
        private void startHumanLikeClicks(){
            // stub
        }
    }


    public MultiClickerBot() throws AWTException {
        multiClickerRunnable = new MultiClickerRunnable();
        botThread = new Thread(multiClickerRunnable);
        botThread.setDaemon(true);
    }

    public void beginMultiClick(List<Point> pts, int totalPPDelay, int totalClickDelay, boolean isLinearTraversal){
        ppDelay = totalPPDelay;
        clickDelay = totalClickDelay;
        isLinear = isLinearTraversal;
        points = pts;
        synchronized (runningLock){
            isRunning = true;
        }

        if (botThread.getState() == Thread.State.TERMINATED){
            botThread = new Thread(multiClickerRunnable);
            botThread.setDaemon(true);
        }

        if (!botThread.isAlive()){
            botThread.start();
        }
    }

    public void stopMultiClick(){
        synchronized (runningLock){
            isRunning = false;
        }
    }


}
