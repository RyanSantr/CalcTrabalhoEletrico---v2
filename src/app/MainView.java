package app;

import app.layout.FooterStatusBar;
import app.layout.HeaderBar;
import app.panels.ChargeSquarePane;
import app.panels.FormulaPanel;
import app.panels.InputPanel;
import app.panels.ResultPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.PhysicsCalculator;

public class MainView extends StackPane {

    private final PhysicsCalculator calculator = new PhysicsCalculator();
    private final InputPanel inputPanel = new InputPanel();
    private final ChargeSquarePane chargeSquarePane = new ChargeSquarePane();
    private final FormulaPanel formulaPanel = new FormulaPanel();
    private final ResultPanel resultPanel = new ResultPanel();
    private final FooterStatusBar footer = new FooterStatusBar();

    public MainView() {
        getStyleClass().add("app-root");

        BackgroundLayer background = new BackgroundLayer();
        background.prefWidthProperty().bind(widthProperty());
        background.prefHeightProperty().bind(heightProperty());

        HeroCharacterPane hero = new HeroCharacterPane();
        hero.prefWidthProperty().bind(widthProperty().multiply(0.33));
        hero.prefHeightProperty().bind(heightProperty().subtract(80));
        StackPane.setAlignment(hero, Pos.BOTTOM_LEFT);
        StackPane.setMargin(hero, new Insets(96, 0, 0, 18));

        ElectricSparksPane sparks = new ElectricSparksPane();
        sparks.prefWidthProperty().bind(widthProperty());
        sparks.prefHeightProperty().bind(heightProperty());

        BorderPane shell = new BorderPane();
        shell.setPadding(new Insets(18, 22, 14, 22));
        shell.setTop(new HeaderBar());
        shell.setCenter(createContent());
        shell.setBottom(footer);

        getChildren().addAll(background, hero, sparks, shell);
        wireActions();
        playEntrance();
    }

    private GridPane createContent() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("content-grid");
        grid.setHgap(18);
        grid.setVgap(18);
        BorderPane.setMargin(grid, new Insets(18, 0, 12, 0));

        ColumnConstraints leftSpace = new ColumnConstraints();
        leftSpace.setPercentWidth(31);
        ColumnConstraints middle = new ColumnConstraints();
        middle.setPercentWidth(30);
        ColumnConstraints right = new ColumnConstraints();
        right.setPercentWidth(39);
        grid.getColumnConstraints().addAll(leftSpace, middle, right);

        RowConstraints top = new RowConstraints();
        top.setPercentHeight(58);
        RowConstraints bottom = new RowConstraints();
        bottom.setPercentHeight(42);
        grid.getRowConstraints().addAll(top, bottom);

        VBox leftSpacer = new VBox();
        grid.add(leftSpacer, 0, 0, 1, 2);
        grid.add(inputPanel, 1, 0);
        grid.add(chargeSquarePane, 2, 0);
        grid.add(formulaPanel, 1, 1);
        grid.add(resultPanel, 2, 1);

        GridPane.setVgrow(inputPanel, Priority.ALWAYS);
        GridPane.setVgrow(chargeSquarePane, Priority.ALWAYS);
        GridPane.setVgrow(formulaPanel, Priority.ALWAYS);
        GridPane.setVgrow(resultPanel, Priority.ALWAYS);
        return grid;
    }

    private void wireActions() {
        inputPanel.setOnCalculate((chargePc, sideCm) -> {
            var result = calculator.calculateFromUserUnits(chargePc, sideCm);
            resultPanel.update(result);
            footer.setStatus("CALCULO OK");
        });
        inputPanel.calculate();
    }

    private void playEntrance() {
        MotionEffects.enterFromRight(inputPanel, 120);
        MotionEffects.enterFromRight(chargeSquarePane, 230);
        MotionEffects.enterFromRight(formulaPanel, 340);
        MotionEffects.enterFromBottom(resultPanel, 460);
    }
}
