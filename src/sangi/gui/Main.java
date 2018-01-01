package sangi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionAdapter;
import sangi.gui.multiclicker.MultiClickerController;
import sangi.gui.multiclicker.MultiClickerInputHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("multiclicker/MultiClickerGUI.fxml"));

        // Init Variables
        Parent root = loader.load();
        MultiClickerController multiClickerController = loader.getController();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        MultiClickerInputHandler multiClickerInputHandler = new MultiClickerInputHandler(multiClickerController);


        // Remove logging from jnativehook
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        // Setup global key listener
        GlobalScreen.registerNativeHook();

        GlobalScreen.addNativeKeyListener(new JavaFXKeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                // Handle Starting/Stopping of bot and recording of new keys
                multiClickerInputHandler.handleKeyPressed(keyEvent);

            }

            @Override
            public void keyReleased(KeyEvent keyEvent){
                // Handle Saving/Deleting of mouse points
                multiClickerInputHandler.handleKeyReleased(keyEvent);
            }
        });

        GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionAdapter(){
            @Override
            public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
                super.nativeMouseMoved(nativeMouseEvent);
                // Update current position of mouse
                multiClickerInputHandler.setCurrentMousePosition(nativeMouseEvent.getPoint());
            }
        });

        // Close global key listener on application close
        primaryStage.setOnCloseRequest(event -> {
            try {
                GlobalScreen.unregisterNativeHook();
            }
            catch (NativeHookException ex) {
                ex.printStackTrace();
            }
            System.runFinalization();
            System.exit(0);
        });

        primaryStage.setTitle("ClickBot");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
