package app.panels;

import app.components.TechButton;
import app.components.TechCard;
import javafx.scene.layout.VBox;

public class QuickPresetsPanel extends TechCard {

    public enum Preset {
        ALL_POSITIVE,
        ALTERNATING,
        SYMMETRIC,
        LAST_SESSION
    }

    private java.util.function.Consumer<Preset> presetAction = preset -> { };

    public QuickPresetsPanel() {
        super("QUICK PRESETS");
        VBox list = new VBox(10,
                button("+   All Positive Charges", Preset.ALL_POSITIVE),
                button("-   Alternating Charges", Preset.ALTERNATING),
                button("+-  Symmetric Setup", Preset.SYMMETRIC),
                button("O   Load Last Session", Preset.LAST_SESSION)
        );
        addContent(list);
    }

    public void setOnPreset(java.util.function.Consumer<Preset> action) {
        presetAction = action;
    }

    private TechButton button(String text, Preset preset) {
        TechButton button = new TechButton(text, false);
        button.getStyleClass().add("preset-button");
        button.setOnAction(event -> presetAction.accept(preset));
        return button;
    }
}
