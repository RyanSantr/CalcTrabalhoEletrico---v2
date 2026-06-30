package app.panels;

import app.components.TechButton;
import app.components.TechCard;
import app.model.ChargeCalculatorModel.ForceComponent;
import app.model.ChargeCalculatorModel.ForceResult;
import java.util.Locale;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ResultPanel extends TechCard {

    private final Label magnitude = new Label("1.247 x 10^-3 N");
    private final Label direction = new Label("Direction: 45.26 deg (NE)");
    private final VectorPane vectorPane = new VectorPane();
    private final GridPane components = new GridPane();

    public ResultPanel() {
        super("RESULT");

        Label label = new Label("NET ELECTRIC FORCE\nAT CENTER (O)");
        label.getStyleClass().add("result-heading");

        magnitude.getStyleClass().add("result-number");
        direction.getStyleClass().add("result-direction");

        TechButton detail = new TechButton("MAGNITUDE & DIRECTION", false);
        detail.getStyleClass().add("compact-button");

        VBox left = new VBox(14, label, magnitude, direction, detail);
        HBox.setHgrow(left, Priority.ALWAYS);

        components.getStyleClass().add("components-grid");
        components.setHgap(20);
        components.setVgap(10);

        VBox right = new VBox(12, vectorPane, components);
        right.setMinWidth(330);

        HBox content = new HBox(26, left, right);
        addContent(content);
    }

    public void update(ForceResult result) {
        magnitude.setText(scientific(result.magnitude()) + " N");
        direction.setText(String.format(Locale.US, "Direction: %.2f deg (%s)", result.angleDegrees(), result.direction()));
        vectorPane.update(result);
        components.getChildren().clear();

        int row = 0;
        for (ForceComponent component : result.components()) {
            addComponent(row++, component.label(), scientific(component.magnitude()), arrow(component.angleDegrees()));
        }
        addComponent(row, "Fnet", scientific(result.magnitude()), arrow(result.angleDegrees()));
    }

    private void addComponent(int row, String label, String value, String arrow) {
        Label left = new Label(label);
        Label middle = new Label(value + " N");
        Label right = new Label(arrow);
        left.getStyleClass().add(row == 4 ? "component-net" : "component-label");
        middle.getStyleClass().add(row == 4 ? "component-net" : "component-value");
        right.getStyleClass().add(row == 4 ? "component-net" : "component-arrow");
        components.add(left, 0, row);
        components.add(middle, 1, row);
        components.add(right, 2, row);
    }

    private String arrow(double angle) {
        double normalized = (angle + 360) % 360;
        if (normalized >= 337.5 || normalized < 22.5) return "->";
        if (normalized < 67.5) return "NE";
        if (normalized < 112.5) return "^";
        if (normalized < 157.5) return "NW";
        if (normalized < 202.5) return "<-";
        if (normalized < 247.5) return "SW";
        if (normalized < 292.5) return "v";
        return "SE";
    }

    private String scientific(double value) {
        if (value == 0) {
            return "0.000 x 10^0";
        }
        int exponent = (int) Math.floor(Math.log10(Math.abs(value)));
        double mantissa = value / Math.pow(10, exponent);
        return String.format(Locale.US, "%.3f x 10^%d", mantissa, exponent);
    }

    private static class VectorPane extends javafx.scene.layout.Pane {
        private ForceResult result;

        VectorPane() {
            getStyleClass().add("vector-pane");
            setMinSize(260, 118);
            setPrefSize(260, 118);
        }

        void update(ForceResult result) {
            this.result = result;
            redraw();
        }

        private void redraw() {
            getChildren().clear();
            double cx = 130;
            double cy = 59;
            getChildren().add(axis(cx, 8, cx, 108));
            getChildren().add(axis(34, cy, 232, cy));
            getChildren().add(new Text(cx + 8, 26, "+y"));
            getChildren().add(new Text(234, cy + 4, "+x"));
            getChildren().add(squareMarker(74, 24, "+"));
            getChildren().add(squareMarker(186, 24, "-"));
            getChildren().add(squareMarker(74, 94, "+"));
            getChildren().add(squareMarker(186, 94, "-"));

            if (result != null && result.magnitude() > 0) {
                double angle = Math.toRadians(result.angleDegrees());
                double length = 52;
                double ex = cx + Math.cos(angle) * length;
                double ey = cy - Math.sin(angle) * length;
                Line vector = new Line(cx, cy, ex, ey);
                vector.getStyleClass().add("force-vector");
                getChildren().add(vector);
                getChildren().add(new Circle(ex, ey, 5, Color.web("#245BFF")));
            }

            Circle center = new Circle(cx, cy, 10, Color.WHITE);
            center.setStroke(Color.web("#111111"));
            getChildren().add(center);
        }

        private Line axis(double sx, double sy, double ex, double ey) {
            Line line = new Line(sx, sy, ex, ey);
            line.getStyleClass().add("axis-line");
            line.getStrokeDashArray().setAll(7.0, 5.0);
            return line;
        }

        private Group squareMarker(double x, double y, String sign) {
            Circle circle = new Circle(x, y, 13, "+".equals(sign) ? Color.web("#245BFF") : Color.web("#111111"));
            Text text = new Text(x - 4, y + 5, sign);
            text.getStyleClass().add("mini-charge-sign");
            return new Group(circle, text);
        }
    }
}
