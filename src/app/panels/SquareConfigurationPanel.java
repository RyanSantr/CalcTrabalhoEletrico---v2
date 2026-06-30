package app.panels;

import app.components.TechCard;
import app.model.ChargeCalculatorModel.ChargeInput;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class SquareConfigurationPanel extends TechCard {

    private final DiagramPane diagram = new DiagramPane();

    public SquareConfigurationPanel() {
        super("SQUARE CONFIGURATION");
        Label caption = new Label("Charges at the corners of a square. Side length = a meters.");
        caption.getStyleClass().add("small-muted-text");
        caption.setWrapText(true);
        addContent(diagram, caption);
    }

    public void update(ChargeInput input) {
        diagram.update(input);
    }

    private static class DiagramPane extends Pane {

        private ChargeInput input = new ChargeInput(2E-6, -3E-6, 4.5E-6, -1.5E-6, 0.25);

        DiagramPane() {
            getStyleClass().add("square-diagram");
            setMinSize(360, 300);
            setPrefSize(520, 340);
            redraw();
        }

        void update(ChargeInput input) {
            this.input = input;
            redraw();
        }

        private void redraw() {
            getChildren().clear();

            double left = 70;
            double top = 46;
            double size = 240;
            double right = left + size;
            double bottom = top + size;
            double centerX = left + size / 2;
            double centerY = top + size / 2;

            getChildren().addAll(
                    dashed(left, top, right, top),
                    dashed(right, top, right, bottom),
                    dashed(right, bottom, left, bottom),
                    dashed(left, bottom, left, top),
                    dashed(left, top, right, bottom),
                    dashed(right, top, left, bottom)
            );
            getChildren().addAll(label("a", centerX - 7, top - 18), label("a", right + 22, centerY),
                    label("a", centerX - 7, bottom + 34), label("a", left - 34, centerY), label("O", centerX - 9, centerY + 7));
            getChildren().addAll(charge("q1", input.q1(), left, top), charge("q2", input.q2(), right, top),
                    charge("q3", input.q3(), left, bottom), charge("q4", input.q4(), right, bottom));
        }

        private Line dashed(double sx, double sy, double ex, double ey) {
            Line line = new Line(sx, sy, ex, ey);
            line.getStyleClass().add("diagram-line");
            line.getStrokeDashArray().setAll(7.0, 6.0);
            return line;
        }

        private Text label(String text, double x, double y) {
            Text label = new Text(x, y, text);
            label.getStyleClass().add("diagram-label");
            return label;
        }

        private Group charge(String name, double value, double x, double y) {
            boolean positive = value >= 0;
            Circle circle = new Circle(x, y, 18, positive ? Color.web("#245BFF") : Color.web("#111111"));
            circle.getStyleClass().add("diagram-charge");

            Text sign = new Text(x - 6, y + 7, positive ? "+" : "-");
            sign.getStyleClass().add("charge-sign");

            Text label = new Text(x - 18, y - 34, name);
            label.getStyleClass().add("diagram-label");

            return new Group(circle, sign, label);
        }
    }
}
