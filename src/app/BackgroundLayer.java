package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class BackgroundLayer extends StackPane {

    public BackgroundLayer() {
        getStyleClass().add("background-layer");

        ImageView background = new ImageView(new Image(resource("/assets/background.png")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(widthProperty());
        background.fitHeightProperty().bind(heightProperty());

        Region overlay = new Region();
        overlay.getStyleClass().add("background-overlay");
        overlay.prefWidthProperty().bind(widthProperty());
        overlay.prefHeightProperty().bind(heightProperty());

        Pane lines = new Pane();
        lines.getStyleClass().add("parallax-lines");
        lines.setMouseTransparent(true);

        getChildren().addAll(background, overlay, lines);
    }

    private String resource(String path) {
        var url = getClass().getResource(path);
        return url == null ? "" : url.toExternalForm();
    }
}
