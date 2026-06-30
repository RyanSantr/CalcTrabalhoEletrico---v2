package app.components;

import javafx.scene.control.Button;

public class TechButton extends Button {

    public TechButton(String text, boolean primary) {
        super(text);
        getStyleClass().addAll("tech-button", primary ? "primary-button" : "secondary-button");
        setMaxWidth(Double.MAX_VALUE);
    }
}
