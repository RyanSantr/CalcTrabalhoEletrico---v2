package app;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class ElectricSparksPane extends Pane {

    private final Random random = new Random();

    public ElectricSparksPane() {
        setMouseTransparent(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(260), event -> spawnSpark()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void spawnSpark() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        double x = 40 + random.nextDouble() * Math.max(80, getWidth() * 0.35);
        double y = 90 + random.nextDouble() * Math.max(100, getHeight() * 0.72);
        double size = 10 + random.nextDouble() * 24;
        Color color = random.nextBoolean() ? Color.web("#FFD800") : Color.web("#00A3FF");

        Line spark = new Line(x, y, x + size, y - size * 0.45);
        spark.setStroke(color);
        spark.setStrokeWidth(1.8);

        Circle dot = new Circle(x + size, y - size * 0.45, 2.2, color);
        getChildren().addAll(spark, dot);

        FadeTransition fade = new FadeTransition(Duration.millis(460), spark);
        fade.setFromValue(0.85);
        fade.setToValue(0);
        fade.setOnFinished(event -> getChildren().removeAll(spark, dot));
        fade.play();

        FadeTransition dotFade = new FadeTransition(Duration.millis(460), dot);
        dotFade.setFromValue(0.9);
        dotFade.setToValue(0);
        dotFade.play();
    }
}
