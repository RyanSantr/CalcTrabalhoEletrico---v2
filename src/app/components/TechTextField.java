package app.components;

import javafx.scene.control.TextField;

public class TechTextField extends TextField {

    public TechTextField(String value) {
        super(value);
        getStyleClass().add("tech-input");
    }

    public void setError(boolean enabled) {
        getStyleClass().remove("input-error");
        if (enabled) {
            getStyleClass().add("input-error");
        }
    }
}
