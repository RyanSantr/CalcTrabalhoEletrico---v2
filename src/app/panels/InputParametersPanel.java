package app.panels;

import app.components.TechButton;
import app.components.TechCard;
import app.components.UnitField;
import app.model.ChargeCalculatorModel.ChargeInput;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InputParametersPanel extends TechCard {

    private final UnitField q1 = new UnitField("q1", "2.00e-6", "C");
    private final UnitField q2 = new UnitField("q2", "-3.00e-6", "C");
    private final UnitField q3 = new UnitField("q3", "4.50e-6", "C");
    private final UnitField q4 = new UnitField("q4", "-1.50e-6", "C");
    private final UnitField side = new UnitField("a", "0.250", "m");
    private final Label message = new Label("Enter charges and square side length.");
    private Runnable calculateAction = () -> { };
    private Runnable resetAction = () -> { };

    public InputParametersPanel() {
        super("INPUT PARAMETERS");

        Label hint = new Label("Charges (Coulombs)");
        hint.getStyleClass().add("small-muted-text");

        Label sideHint = new Label("Side Length (meters)");
        sideHint.getStyleClass().add("small-muted-text");

        VBox fields = new VBox(12, hint, q1, q2, q3, q4, sideHint, side);

        TechButton calculate = new TechButton("CALCULATE", true);
        calculate.setOnAction(event -> calculateAction.run());

        TechButton reset = new TechButton("RESET", false);
        reset.setOnAction(event -> resetAction.run());

        HBox actions = new HBox(14, calculate, reset);
        actions.setAlignment(Pos.CENTER);
        HBox.setHgrow(calculate, Priority.ALWAYS);
        HBox.setHgrow(reset, Priority.ALWAYS);

        message.getStyleClass().add("input-message");
        message.setWrapText(true);

        addContent(fields, actions, message);
    }

    public ChargeInput readInput() {
        clearErrors();
        try {
            double sideValue = parse(side);
            if (sideValue <= 0) {
                side.setError(true);
                throw new IllegalArgumentException("Side length must be greater than zero.");
            }
            return new ChargeInput(parse(q1), parse(q2), parse(q3), parse(q4), sideValue);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Use valid decimal or scientific notation values.");
        }
    }

    public void setMessage(String text) {
        message.setText(text);
    }

    public void setOnCalculate(Runnable action) {
        calculateAction = action;
    }

    public void setOnReset(Runnable action) {
        resetAction = action;
    }

    public void setValues(double q1Value, double q2Value, double q3Value, double q4Value, double sideValue) {
        q1.setValue(format(q1Value));
        q2.setValue(format(q2Value));
        q3.setValue(format(q3Value));
        q4.setValue(format(q4Value));
        side.setValue(String.format(java.util.Locale.US, "%.3f", sideValue));
    }

    private double parse(UnitField field) {
        String normalized = field.getValue().trim().replace(",", ".");
        if (normalized.isBlank()) {
            field.setError(true);
            throw new NumberFormatException();
        }
        double value = Double.parseDouble(normalized);
        if (!Double.isFinite(value)) {
            field.setError(true);
            throw new NumberFormatException();
        }
        return value;
    }

    private void clearErrors() {
        q1.setError(false);
        q2.setError(false);
        q3.setError(false);
        q4.setError(false);
        side.setError(false);
    }

    private String format(double value) {
        return String.format(java.util.Locale.US, "%.2e", value).replace("e-0", "e-").replace("e+0", "e");
    }
}
