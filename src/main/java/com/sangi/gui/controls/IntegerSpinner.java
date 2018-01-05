package main.java.com.sangi.gui.controls;

import javafx.beans.NamedArg;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class IntegerSpinner extends Spinner<Integer> {

    private int defaultVal;
    private int minVal;
    private int maxVal;
    private int amountToStepVal;

    public IntegerSpinner(@NamedArg("defaultValue") int defaultSpinnerValue,
                          @NamedArg("minValue") int minSpinnerValue,
                          @NamedArg("amountToStepValue") int amountToStepSpinnerValue){
        super();
        defaultVal = defaultSpinnerValue;
        minVal = minSpinnerValue;
        maxVal = Integer.MAX_VALUE;
        amountToStepVal = amountToStepSpinnerValue;
        setupValueFactory();
        setupTextFormatter();
        setupListener();
        addListenerKeyChange();
    }

    private void setupValueFactory() {
        SpinnerValueFactory<Integer> integerSpinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal, defaultVal,amountToStepVal);

        this.setValueFactory(integerSpinnerValueFactory);
    }

    private void setupTextFormatter() {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([0-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        this.getEditor().setTextFormatter(
                new TextFormatter<>(new IntegerStringConverter(), defaultVal, integerFilter));
    }

    private void setupListener(){
        this.valueProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    try {
                        if (newValue == null) {
                            this.getValueFactory().setValue(defaultVal);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                })
        );
    }

    private void addListenerKeyChange(){
        this.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            commitEditorText();
        }));
    }

    private void commitEditorText(){
        if (!isEditable()) { return; }
        String text = getEditor().getText();
        SpinnerValueFactory<Integer> valueFactory = getValueFactory();
        if (valueFactory != null){
            StringConverter converter = valueFactory.getConverter();
            if (converter != null){
                Integer value = (Integer) converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

}
