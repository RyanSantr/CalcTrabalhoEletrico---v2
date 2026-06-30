package app.layout;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HeaderBar extends HBox {

    public HeaderBar() {
        getStyleClass().add("header-bar");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(28);

        Label bolt = new Label("HIGH VOLTAGE");
        bolt.getStyleClass().add("header-bolt");

        Label title = new Label("TRABALHO ELETRICO");
        title.getStyleClass().add("header-title");

        Label subtitle = new Label("Montagem de Cargas");
        subtitle.getStyleClass().add("header-subtitle");

        VBox copy = new VBox(2, title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox nav = new HBox(10,
                navTab("Inicio", true),
                navTab("Entradas", false),
                navTab("Configuracao", false),
                navTab("Formula", false),
                navTab("Resultado", false)
        );
        nav.getStyleClass().add("header-nav");

        getChildren().addAll(bolt, copy, spacer, nav);
    }

    private Label navTab(String text, boolean active) {
        Label label = new Label(text);
        label.getStyleClass().add(active ? "nav-tab-active" : "nav-tab");
        return label;
    }
}
