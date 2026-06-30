package app.panels;

import app.components.TechCard;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CoulombLawPanel extends TechCard {

    public CoulombLawPanel() {
        super("COULOMB'S LAW");

        Label formula = new Label("F = k |q1 q2| / r^2");
        formula.getStyleClass().add("formula-display");

        VBox details = new VBox(8,
                line("F", "Electric force (Newtons)"),
                line("k", "Coulomb constant (8.9875 x 10^9 N.m^2/C^2)"),
                line("q1, q2", "Charges (Coulombs)"),
                line("r", "Distance between charges (meters)")
        );

        addContent(formula, details);
    }

    private Label line(String key, String value) {
        Label label = new Label(key + "  =  " + value);
        label.getStyleClass().add("formula-line");
        return label;
    }
}
