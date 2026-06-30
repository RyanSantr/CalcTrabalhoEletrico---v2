package app.layout;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FooterStatusBar extends HBox {

    private final Label status = new Label("ONLINE");

    public FooterStatusBar() {
        getStyleClass().add("status-bar");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(16);

        Label stripes = new Label("/////");
        stripes.getStyleClass().add("footer-stripes");

        Label title = new Label("SYSTEM STATUS");
        title.getStyleClass().add("status-title");

        status.getStyleClass().add("status-online");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label version = new Label("v1.0.0");
        version.getStyleClass().add("status-version");

        getChildren().addAll(stripes, title, status, spacer, new Label("|"), version);
    }

    public void setStatus(String text) {
        status.setText(text);
    }
}
