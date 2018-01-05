package main.java.com.sangi.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import main.java.com.sangi.gui.handlers.AutoClickerInputHandler;
import main.java.com.sangi.gui.handlers.MultiClickerInputHandler;

public class MainController {

    @FXML private TabPane tabPane;
    @FXML private Tab autoClickerTab;
    @FXML private Tab multiClickerTab;
    @FXML private AutoClickerController autoClickerController;
    @FXML private MultiClickerController multiClickerController;

    private MultiClickerInputHandler multiClickerInputHandler;
    private AutoClickerInputHandler autoClickerInputHandler;

    @FXML
    private void initialize(){
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public boolean isAutoClickerSelected() {
        return tabPane.getSelectionModel().getSelectedItem().equals(autoClickerTab);
    }

    public boolean isMultiClickerSelected() {
        return tabPane.getSelectionModel().getSelectedItem().equals(multiClickerTab);
    }

    public AutoClickerController getAutoClickerController() {
        return autoClickerController;
    }

    public MultiClickerController getMultiClickerController() {
        return multiClickerController;
    }


}
