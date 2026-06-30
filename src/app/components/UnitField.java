package app.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class UnitField extends HBox {

    private final TechTextField field;

    public UnitField(String symbol, String value, String unit) {
        getStyleClass().add("unit-field-row");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(12);

        Label symbolLabel = new Label(symbol);
        symbolLabel.getStyleClass().add("unit-symbol");

        field = new TechTextField(value);
        HBox.setHgrow(field, Priority.ALWAYS);

        Label unitLabel = new Label(unit);
        unitLabel.getStyleClass().add("unit-box");

        getChildren().addAll(symbolLabel, field, unitLabel);
    }

    public String getValue() {
        return field.getText();
    }

    public void setValue(String value) {
        field.setText(value);
        field.setError(false);
    }

    public void setError(boolean enabled) {
        field.setError(enabled);
    }
}
