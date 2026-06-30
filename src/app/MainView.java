package app;

import app.layout.FooterStatusBar;
import app.layout.HeaderBar;
import app.layout.HeroPanel;
import app.model.ChargeCalculatorModel;
import app.model.ChargeCalculatorModel.ChargeInput;
import app.model.ChargeCalculatorModel.ForceResult;
import app.panels.ConversionsPanel;
import app.panels.CoulombLawPanel;
import app.panels.InputParametersPanel;
import app.panels.QuickPresetsPanel;
import app.panels.ResultPanel;
import app.panels.SquareConfigurationPanel;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {

    private final ChargeCalculatorModel calculator = new ChargeCalculatorModel();
    private final InputParametersPanel inputPanel = new InputParametersPanel();
    private final SquareConfigurationPanel squarePanel = new SquareConfigurationPanel();
    private final CoulombLawPanel lawPanel = new CoulombLawPanel();
    private final ConversionsPanel conversionsPanel = new ConversionsPanel();
    private final ResultPanel resultPanel = new ResultPanel();
    private final QuickPresetsPanel presetsPanel = new QuickPresetsPanel();
    private final FooterStatusBar footer = new FooterStatusBar();
    private ChargeInput lastInput = new ChargeInput(2E-6, -3E-6, 4.5E-6, -1.5E-6, 0.25);

    public MainView() {
        getStyleClass().add("root-pane");
        setPadding(new Insets(10));
        setTop(new HeaderBar());
        setCenter(createContent());
        setBottom(footer);
        wireActions();
        calculate();
    }

    private HBox createContent() {
        HeroPanel hero = new HeroPanel();
        hero.setMinWidth(430);
        hero.setPrefWidth(520);
        HBox.setHgrow(hero, Priority.SOMETIMES);

        GridPane dashboard = new GridPane();
        dashboard.getStyleClass().add("dashboard-grid");
        dashboard.setHgap(16);
        dashboard.setVgap(16);
        HBox.setHgrow(dashboard, Priority.ALWAYS);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(32);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(38);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(30);
        dashboard.getColumnConstraints().addAll(c1, c2, c3);

        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(48);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(31);
        RowConstraints r3 = new RowConstraints();
        r3.setPercentHeight(21);
        dashboard.getRowConstraints().addAll(r1, r2, r3);

        VBox rightStack = new VBox(10, lawPanel, conversionsPanel, presetsPanel);
        VBox.setVgrow(lawPanel, Priority.ALWAYS);
        VBox.setVgrow(conversionsPanel, Priority.ALWAYS);
        VBox.setVgrow(presetsPanel, Priority.ALWAYS);

        dashboard.add(inputPanel, 0, 0);
        dashboard.add(squarePanel, 1, 0);
        dashboard.add(rightStack, 2, 0, 1, 3);
        dashboard.add(resultPanel, 0, 1, 2, 2);

        GridPane.setHgrow(inputPanel, Priority.ALWAYS);
        GridPane.setHgrow(squarePanel, Priority.ALWAYS);
        GridPane.setHgrow(resultPanel, Priority.ALWAYS);
        GridPane.setVgrow(inputPanel, Priority.ALWAYS);
        GridPane.setVgrow(squarePanel, Priority.ALWAYS);
        GridPane.setVgrow(resultPanel, Priority.ALWAYS);

        HBox content = new HBox(16, hero, dashboard);
        content.getStyleClass().add("main-content");
        return content;
    }

    private void wireActions() {
        inputPanel.setOnCalculate(this::calculate);
        inputPanel.setOnReset(() -> {
            inputPanel.setValues(2E-6, -3E-6, 4.5E-6, -1.5E-6, 0.25);
            calculate();
        });

        presetsPanel.setOnPreset(preset -> {
            switch (preset) {
                case ALL_POSITIVE -> inputPanel.setValues(2E-6, 3E-6, 4.5E-6, 1.5E-6, 0.25);
                case ALTERNATING -> inputPanel.setValues(2E-6, -3E-6, 4.5E-6, -1.5E-6, 0.25);
                case SYMMETRIC -> inputPanel.setValues(2E-6, -2E-6, 2E-6, -2E-6, 0.25);
                case LAST_SESSION -> inputPanel.setValues(lastInput.q1(), lastInput.q2(), lastInput.q3(), lastInput.q4(), lastInput.sideMeters());
            }
            calculate();
        });
    }

    private void calculate() {
        try {
            ChargeInput input = inputPanel.readInput();
            ForceResult result = calculator.calculateAtCenter(input);
            lastInput = input;
            squarePanel.update(input);
            resultPanel.update(result);
            footer.setStatus("ONLINE");
            inputPanel.setMessage("Calculation updated. Model uses a 1 nC positive test charge at center O.");
        } catch (IllegalArgumentException exception) {
            footer.setStatus("INPUT ERROR");
            inputPanel.setMessage(exception.getMessage());
        }
    }
}
