package app.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FormulaPanel extends VBox {

    public FormulaPanel() {
        getStyleClass().addAll("cyber-card", "formula-panel");
        setSpacing(14);

        Label title = new Label("03. FORMULA E CONVERSOES");
        title.getStyleClass().add("cyber-card-title");

        Label formulas = new Label("""
                W = U
                U = 4(-kq^2/a) + 2(kq^2/(a√2))
                U = (kq^2/a)(√2 - 4)
                """);
        formulas.getStyleClass().add("formula-text");

        Label conversions = new Label("""
                1 pC = 10^-12 C
                1 cm = 10^-2 m
                """);
        conversions.getStyleClass().add("conversion-text");

        Label note = new Label("Use q em picoCoulombs e a em centimetros.");
        note.getStyleClass().add("result-note");

        getChildren().addAll(title, formulas, conversions, note);
    }
}
