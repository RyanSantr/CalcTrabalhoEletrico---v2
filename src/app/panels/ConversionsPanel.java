package app.panels;

import app.components.TechCard;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ConversionsPanel extends TechCard {

    public ConversionsPanel() {
        super("CONVERSIONS");
        GridPane grid = new GridPane();
        grid.getStyleClass().add("conversion-grid");
        grid.setHgap(24);
        grid.setVgap(10);
        addRow(grid, 0, "1 nC", "1e-9 C");
        addRow(grid, 1, "1 uC", "1e-6 C");
        addRow(grid, 2, "1 mC", "1e-3 C");
        addRow(grid, 3, "1 C", "1 C");
        addContent(grid);
    }

    private void addRow(GridPane grid, int row, String from, String to) {
        Label left = new Label(from);
        Label equals = new Label("=");
        Label right = new Label(to);
        left.getStyleClass().add("conversion-text");
        equals.getStyleClass().add("conversion-text");
        right.getStyleClass().add("conversion-text");
        grid.add(left, 0, row);
        grid.add(equals, 1, row);
        grid.add(right, 2, row);
    }
}
