package app.panels;

import app.MotionEffects;
import java.util.function.BiConsumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.NumberUtils;

public class InputPanel extends VBox {

    private final TextField chargeField = new TextField("2,30");
    private final TextField sideField = new TextField("64");
    private final Label errorLabel = new Label("");
    private BiConsumer<Double, Double> calculateAction = (q, a) -> { };

    public InputPanel() {
        getStyleClass().addAll("cyber-card", "input-panel");
        setSpacing(16);

        Label index = new Label("01. ENTRADAS");
        index.getStyleClass().add("cyber-card-title");

        Label subtitle = new Label("Digite os valores do exercicio");
        subtitle.getStyleClass().add("cyber-subtitle");

        GridPane fields = new GridPane();
        fields.setHgap(12);
        fields.setVgap(12);
        fields.add(fieldLabel("Carga q"), 0, 0);
        fields.add(unitField(chargeField, "pC"), 0, 1);
        fields.add(fieldLabel("Lado a"), 0, 2);
        fields.add(unitField(sideField, "cm"), 0, 3);

        Label helper = new Label("Aceita virgula ou ponto");
        helper.getStyleClass().add("helper-line");

        Button calculate = new Button("CALCULAR  >>");
        calculate.getStyleClass().addAll("primary-button", "motion-button");
        calculate.setOnAction(event -> calculate());

        Button clear = new Button("LIMPAR  >>");
        clear.getStyleClass().addAll("secondary-button", "motion-button");
        clear.setOnAction(event -> clear());

        HBox actions = new HBox(14, calculate, clear);
        HBox.setHgrow(calculate, Priority.ALWAYS);
        HBox.setHgrow(clear, Priority.ALWAYS);
        calculate.setMaxWidth(Double.MAX_VALUE);
        clear.setMaxWidth(Double.MAX_VALUE);

        errorLabel.getStyleClass().add("error-label");
        errorLabel.setWrapText(true);

        getChildren().addAll(index, subtitle, fields, helper, actions, errorLabel);
    }

    public void setOnCalculate(BiConsumer<Double, Double> action) {
        calculateAction = action;
    }

    public void calculate() {
        try {
            clearError();
            double chargePc = NumberUtils.parseFlexibleDouble(chargeField.getText(), "q");
            double sideCm = NumberUtils.parseFlexibleDouble(sideField.getText(), "a");

            if (sideCm <= 0) {
                sideField.getStyleClass().add("input-error");
                throw new IllegalArgumentException("O lado a precisa ser maior que zero.");
            }

            calculateAction.accept(chargePc, sideCm);
        } catch (IllegalArgumentException exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

    public void clear() {
        chargeField.clear();
        sideField.clear();
        clearError();
        chargeField.requestFocus();
    }

    private void clearError() {
        errorLabel.setText("");
        chargeField.getStyleClass().remove("input-error");
        sideField.getStyleClass().remove("input-error");
    }

    private Label fieldLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("input-label");
        return label;
    }

    private HBox unitField(TextField field, String unit) {
        field.getStyleClass().add("input-field");

        Label unitLabel = new Label(unit);
        unitLabel.getStyleClass().add("unit-label");

        HBox box = new HBox(10, field, unitLabel);
        box.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(field, Priority.ALWAYS);
        return box;
    }

    public void playResultPulse() {
        MotionEffects.pulse(this);
    }
}
