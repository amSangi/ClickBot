package sangi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // TODO: Change hot-keys on key press when allowed
    // TODO: Change button text from 'recording...' when clicking out of button
    // TODO: When key pressed is a hot-key, begin auto-clicking


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AutoClickerGUI.fxml"));

        // Init Variables
        Parent root = loader.load();
        AutoClickerGUI controller = loader.getController();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

        // Set Event Handlers


        primaryStage.setTitle("ClickBot");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
