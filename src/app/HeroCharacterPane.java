package app;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class HeroCharacterPane extends StackPane {

    public HeroCharacterPane() {
        getStyleClass().add("hero-character-pane");
        setPickOnBounds(false);

        var url = getClass().getResource("/assets/hero_character.png");
        if (url == null) {
            Label missing = new Label("hero_character.png nao encontrado");
            missing.getStyleClass().add("asset-missing");
            getChildren().add(missing);
            return;
        }

        ImageView character = new ImageView(new Image(url.toExternalForm()));
        character.setPreserveRatio(true);
        character.setSmooth(true);
        character.fitHeightProperty().bind(heightProperty().multiply(0.92));
        character.fitWidthProperty().bind(widthProperty().multiply(1.05));
        character.setEffect(new DropShadow(36, Color.web("#00A3FF", 0.42)));
        StackPane.setAlignment(character, Pos.BOTTOM_LEFT);
        getChildren().add(character);

        MotionEffects.floatIdle(character);
    }
}
