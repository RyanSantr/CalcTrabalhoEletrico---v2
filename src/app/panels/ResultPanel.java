package app.panels;

import app.MotionEffects;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PhysicsCalculator.WorkResult;
import util.NumberUtils;

public class ResultPanel extends VBox {

    private final Label resultNumber = new Label("W = -- J");
    private final Label values = new Label("q = -- pC     a = -- cm");
    private final Label note = new Label("Energia necessaria para montar o sistema");

    public ResultPanel() {
        getStyleClass().addAll("cyber-card", "result-card");
        setSpacing(13);

        Label title = new Label("04. RESULTADO");
        title.getStyleClass().add("cyber-card-title");
        title.setWrapText(true);

        resultNumber.getStyleClass().add("result-number");
        values.getStyleClass().add("result-values");
        note.getStyleClass().add("result-note");
        resultNumber.setWrapText(true);
        values.setWrapText(true);
        note.setWrapText(true);

        HBox chips = new HBox(16, values);
        chips.getStyleClass().add("result-chip-row");

        getChildren().addAll(title, resultNumber, chips, note);
    }

    public void update(WorkResult result) {
        resultNumber.setText("W = " + NumberUtils.formatScientific(result.workJoule()) + " J");
        values.setText("q = " + NumberUtils.formatFixed(result.chargePc(), "0.00") + " pC     a = "
                + NumberUtils.formatDecimal(result.sideCm()) + " cm");
        note.setText(result.workJoule() < 0
                ? "Resultado negativo: interacao atrativa predominante."
                : "Resultado positivo: interacao repulsiva predominante.");
        MotionEffects.pulse(resultNumber);
    }

    public void clear() {
        resultNumber.setText("W = -- J");
        values.setText("q = -- pC     a = -- cm");
        note.setText("Energia necessaria para montar o sistema");
    }
}
