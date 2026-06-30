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

        Label bolt = new Label("LIGHTNING");
        bolt.getStyleClass().add("micro-label");

        Label title = new Label("ELECTRIC CHARGE CALCULATOR");
        title.getStyleClass().add("system-name");

        Label bars = new Label("||||||||||||");
        bars.getStyleClass().add("blue-bars");

        HBox brand = new HBox(14, bolt, title, bars);
        brand.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox nav = new HBox(42,
                navItem("CALCULATOR", true),
                navItem("FORMULA", false),
                navItem("HISTORY", false),
                navItem("THEORY", false)
        );
        nav.getStyleClass().add("top-nav");
        nav.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(brand, spacer, nav);
    }

    private VBox navItem(String text, boolean active) {
        Label icon = new Label(switch (text) {
            case "CALCULATOR" -> "++";
            case "FORMULA" -> "[]";
            case "HISTORY" -> "OO";
            default -> "BK";
        });
        icon.getStyleClass().add("nav-icon");

        Label label = new Label(text);
        label.getStyleClass().add("nav-label");

        VBox box = new VBox(5, icon, label);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add(active ? "nav-item-active" : "nav-item");
        return box;
    }
}
