package app.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ChargeSquarePane extends VBox {

    private final DrawingPane drawing = new DrawingPane();

    public ChargeSquarePane() {
        getStyleClass().addAll("cyber-card", "configuration-panel");
        setSpacing(12);

        Label title = new Label("02. CONFIGURACAO");
        title.getStyleClass().add("cyber-card-title");

        Label subtitle = new Label("Quatro cargas nos vertices de um quadrado");
        subtitle.getStyleClass().add("cyber-subtitle");

        VBox.setVgrow(drawing, Priority.ALWAYS);
        getChildren().addAll(title, subtitle, drawing, legend());
    }

    private javafx.scene.Node legend() {
        Label label = new Label("+q carga positiva     -q carga negativa");
        label.getStyleClass().add("legend-label");
        return label;
    }

    private static class DrawingPane extends Pane {

        DrawingPane() {
            getStyleClass().add("charge-square-drawing");
            setMinHeight(260);
            widthProperty().addListener((obs, oldValue, newValue) -> redraw());
            heightProperty().addListener((obs, oldValue, newValue) -> redraw());
        }

        @Override
        protected void layoutChildren() {
            super.layoutChildren();
            redraw();
        }

        private void redraw() {
            getChildren().clear();
            double w = Math.max(300, getWidth());
            double h = Math.max(250, getHeight());
            double size = Math.min(w * 0.62, h * 0.68);
            double left = (w - size) / 2;
            double top = (h - size) / 2 + 8;
            double right = left + size;
            double bottom = top + size;
            double cx = left + size / 2;
            double cy = top + size / 2;

            addLine(left, top, right, top, false);
            addLine(right, top, right, bottom, false);
            addLine(right, bottom, left, bottom, false);
            addLine(left, bottom, left, top, false);
            addLine(left, top, right, bottom, true);
            addLine(right, top, left, bottom, true);

            getChildren().addAll(text("a", cx - 8, top - 14), text("a", right + 18, cy),
                    text("a", cx - 8, bottom + 30), text("a", left - 30, cy), text("O", cx - 8, cy + 7));

            charge("+q", left, top, "#1677FF");
            charge("-q", right, top, "#FF3358");
            charge("-q", left, bottom, "#FF3358");
            charge("+q", right, bottom, "#1677FF");
        }

        private void addLine(double sx, double sy, double ex, double ey, boolean dashed) {
            Line line = new Line(sx, sy, ex, ey);
            line.getStyleClass().add("square-line-cyber");
            if (dashed) {
                line.getStrokeDashArray().setAll(6.0, 8.0);
            }
            getChildren().add(line);
        }

        private Text text(String value, double x, double y) {
            Text text = new Text(x, y, value);
            text.getStyleClass().add("square-label-cyber");
            return text;
        }

        private void charge(String label, double x, double y, String color) {
            Circle glow = new Circle(x, y, 26, Color.web(color, 0.14));
            Circle circle = new Circle(x, y, 19, Color.web(color));
            circle.getStyleClass().add("charge-dot-cyber");
            Text text = new Text(x - 14, y + 7, label);
            text.getStyleClass().add("charge-label-cyber");
            getChildren().addAll(glow, circle, text);
        }
    }
}
