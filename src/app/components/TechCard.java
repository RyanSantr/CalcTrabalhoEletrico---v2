package app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TechCard extends VBox {

    private final VBox body = new VBox(14);

    public TechCard(String title) {
        getStyleClass().add("tech-card");
        setSpacing(14);
        setPadding(new Insets(20));

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("panel-title");

        Label slash = new Label("///");
        slash.getStyleClass().add("blue-mark");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(12, titleLabel, slash, spacer, corner());
        header.setAlignment(Pos.CENTER_LEFT);

        body.getStyleClass().add("tech-card-body");
        getChildren().addAll(header, body);
    }

    public void addContent(Node... nodes) {
        body.getChildren().addAll(nodes);
    }

    public VBox body() {
        return body;
    }

    private Region corner() {
        Region region = new Region();
        region.getStyleClass().add("corner-accent");
        region.setMinSize(24, 24);
        region.setPrefSize(24, 24);
        region.setMaxSize(24, 24);
        return region;
    }
}
