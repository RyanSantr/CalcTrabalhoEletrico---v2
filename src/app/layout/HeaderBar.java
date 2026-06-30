package app.layout;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
        bolt.setMinWidth(Region.USE_PREF_SIZE);

        Label title = new Label("TRABALHO ELETRICO");
        title.getStyleClass().add("header-title");
        title.setMinWidth(Region.USE_PREF_SIZE);

        Label subtitle = new Label("Montagem de Cargas");
        subtitle.getStyleClass().add("header-subtitle");

        VBox copy = new VBox(2, title, subtitle);
        copy.setMinWidth(290);
        HBox.setHgrow(copy, Priority.ALWAYS);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        FlowPane nav = new FlowPane(10, 8);
        nav.getStyleClass().add("header-nav");
        nav.setAlignment(Pos.CENTER_RIGHT);
        nav.setPrefWrapLength(340);
        nav.getChildren().addAll(
                navTab("Inicio", true),
                navTab("Entradas", false),
                navTab("Configuracao", false),
                navTab("Formula", false),
                navTab("Resultado", false)
        );
        nav.setMaxWidth(440);
        HBox.setHgrow(nav, Priority.SOMETIMES);

        getChildren().addAll(bolt, copy, spacer, nav);
    }

    private Label navTab(String text, boolean active) {
        Label label = new Label(text);
        label.getStyleClass().add(active ? "nav-tab-active" : "nav-tab");
        return label;
    }
}
