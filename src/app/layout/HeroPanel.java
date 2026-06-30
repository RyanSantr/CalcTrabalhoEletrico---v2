package app.layout;

import java.net.URL;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HeroPanel extends StackPane {

    public HeroPanel() {
        getStyleClass().add("hero-panel");

        URL heroUrl = getClass().getResource("/assets/hero_character.png");
        if (heroUrl != null) {
            setStyle("""
                    -fx-background-image: url('%s');
                    -fx-background-repeat: no-repeat;
                    -fx-background-position: center;
                    -fx-background-size: 100%% 100%%;
                    """.formatted(heroUrl.toExternalForm()));
            return;
        }

        VBox copy = new VBox(12);
        copy.getStyleClass().add("hero-copy");
        copy.setAlignment(Pos.TOP_LEFT);
        copy.setMouseTransparent(true);

        Label appName = new Label("ELECTRIC CHARGE CALCULATOR  ||||||||||");
        appName.getStyleClass().add("hero-kicker");

        Label title = new Label("CHARGE\nCALCULATOR");
        title.getStyleClass().add("hero-title");

        Label subtitle = new Label("FOUR-POINT SQUARE\nCONFIGURATION");
        subtitle.getStyleClass().add("hero-subtitle");

        Label stack = new Label("JAVA\nFX   +");
        stack.getStyleClass().add("hero-stack");

        Label phrase = new Label("FORCE CONNECTS,\nACROSS ANY DISTANCE.");
        phrase.getStyleClass().add("hero-phrase");

        Label footer = new Label("DESIGN PROTOTYPE 2026   |   VERSION 1.0.0");
        footer.getStyleClass().add("hero-footer");

        copy.getChildren().addAll(appName, title, subtitle, stack, phrase, footer);
        getChildren().add(copy);
    }
}
