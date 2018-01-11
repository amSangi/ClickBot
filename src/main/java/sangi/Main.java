package main.java.sangi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.java.sangi.gui.JavaFXKeyAdapter;
import main.java.sangi.gui.controllers.MainController;
import main.java.sangi.gui.handlers.AutoClickerInputHandler;
import main.java.sangi.gui.handlers.MultiClickerInputHandler;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionAdapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        // Remove logging from jnativehook
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/Main.fxml"));

        // Init Variables
        Parent root = null;
        try {
            root = loader.load();
        }
        catch (IOException e){
            generateError(e.getMessage());
        }

        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        MainController mainController = loader.getController();

        final MultiClickerInputHandler multiClickerInputHandler = new MultiClickerInputHandler(mainController.getMultiClickerController());
        final AutoClickerInputHandler autoClickerInputHandler = new AutoClickerInputHandler(mainController.getAutoClickerController());

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException e){
            if (e.getCode() == NativeHookException.DARWIN_AXAPI_DISABLED){
                String message = "Apple access for assistive devices is disabled. Please enable access and relaunch the program.";
                generateError(message);
            }
            else {
                generateError(e.getMessage());
            }
        }

        GlobalScreen.addNativeKeyListener(new JavaFXKeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                // Handle Starting/Stopping of bot and recording of new keys
                if (mainController.isMultiClickerSelected()){
                    multiClickerInputHandler.handleKeyPressed(keyEvent);
                }
                else if (mainController.isAutoClickerSelected()){
                    autoClickerInputHandler.handleKeyPressed(keyEvent);
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent){
                // Handle Saving/Deleting of mouse points
                if (mainController.isMultiClickerSelected()) {
                    multiClickerInputHandler.handleKeyReleased(keyEvent);
                }
            }
        });

        GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionAdapter(){
            @Override
            public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
                // Update current position of mouse
                if (mainController.isMultiClickerSelected()) {
                    multiClickerInputHandler.setCurrentMousePosition(nativeMouseEvent.getPoint());
                }
            }
        });


        // Close global key listener on application close
        primaryStage.setOnCloseRequest(event -> {
            try {
                GlobalScreen.unregisterNativeHook();
            }
            catch (NativeHookException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            System.runFinalization();
            System.exit(0);
        });

        primaryStage.setTitle("ClickBot");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void generateError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.CLOSE) {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
