package app;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public final class MotionEffects {

    private MotionEffects() {
    }

    public static void floatIdle(Node node) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2.8), node);
        transition.setByY(-12);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    public static void enterFromRight(Node node, double delayMillis) {
        node.setOpacity(0);
        node.setTranslateX(38);

        FadeTransition fade = new FadeTransition(Duration.millis(520), node);
        fade.setToValue(1);
        fade.setDelay(Duration.millis(delayMillis));

        TranslateTransition slide = new TranslateTransition(Duration.millis(520), node);
        slide.setToX(0);
        slide.setDelay(Duration.millis(delayMillis));

        new ParallelTransition(fade, slide).play();
    }

    public static void enterFromBottom(Node node, double delayMillis) {
        node.setOpacity(0);
        node.setTranslateY(42);

        FadeTransition fade = new FadeTransition(Duration.millis(560), node);
        fade.setToValue(1);
        fade.setDelay(Duration.millis(delayMillis));

        TranslateTransition slide = new TranslateTransition(Duration.millis(560), node);
        slide.setToY(0);
        slide.setDelay(Duration.millis(delayMillis));

        new ParallelTransition(fade, slide).play();
    }

    public static void pulse(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(260), node);
        fade.setFromValue(0.55);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(Duration.millis(260), node);
        scale.setFromX(0.96);
        scale.setFromY(0.96);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }
}
