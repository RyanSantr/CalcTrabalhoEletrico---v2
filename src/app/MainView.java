package app;

import app.layout.FooterStatusBar;
import app.layout.HeaderBar;
import app.panels.ChargeSquarePane;
import app.panels.FormulaPanel;
import app.panels.InputPanel;
import app.panels.ResultPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
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
    private final GridPane contentGrid = new GridPane();
    private final BorderPane shell = new BorderPane();
    private final HeroCharacterPane hero = new HeroCharacterPane();
    private boolean compactLayout;

    public MainView() {
        getStyleClass().add("app-root");

        BackgroundLayer background = new BackgroundLayer();
        background.prefWidthProperty().bind(widthProperty());
        background.prefHeightProperty().bind(heightProperty());

        configureHero();

        ElectricSparksPane sparks = new ElectricSparksPane();
        sparks.prefWidthProperty().bind(widthProperty());
        sparks.prefHeightProperty().bind(heightProperty());

        shell.getStyleClass().add("app-shell");
        shell.setTop(new HeaderBar());
        shell.setCenter(createScrollableContent());
        shell.setBottom(footer);

        getChildren().addAll(background, hero, sparks, shell);
        widthProperty().addListener((observable, oldValue, newValue) -> applyResponsiveLayout(newValue.doubleValue()));
        applyResponsiveLayout(1600);
        wireActions();
        playEntrance();
    }

    private void configureHero() {
        hero.prefWidthProperty().bind(widthProperty().multiply(0.34));
        hero.prefHeightProperty().bind(heightProperty().subtract(80));
        hero.setMouseTransparent(true);
        StackPane.setAlignment(hero, Pos.BOTTOM_LEFT);
        StackPane.setMargin(hero, new Insets(96, 0, 0, 18));
    }

    private ScrollPane createScrollableContent() {
        contentGrid.getStyleClass().add("content-grid");
        contentGrid.setHgap(18);
        contentGrid.setVgap(18);
        contentGrid.setMaxWidth(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(contentGrid);
        scrollPane.getStyleClass().add("content-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        BorderPane.setMargin(scrollPane, new Insets(18, 0, 12, 0));
        return scrollPane;
    }

    private void applyResponsiveLayout(double width) {
        boolean useCompact = width < 1180;
        if (useCompact == compactLayout && !contentGrid.getChildren().isEmpty()) {
            updateShellPadding(width);
            return;
        }

        compactLayout = useCompact;
        updateShellPadding(width);
        contentGrid.getChildren().clear();
        contentGrid.getColumnConstraints().clear();
        contentGrid.getRowConstraints().clear();

        if (useCompact) {
            hero.setOpacity(0.18);
            hero.setTranslateX(-70);
            buildCompactLayout();
        } else {
            hero.setOpacity(0.98);
            hero.setTranslateX(0);
            buildDesktopLayout(width);
        }
    }

    private void updateShellPadding(double width) {
        if (width < 1180) {
            shell.setPadding(new Insets(10, 12, 10, 12));
        } else if (width < 1400) {
            shell.setPadding(new Insets(14, 16, 12, 16));
        } else {
            shell.setPadding(new Insets(18, 22, 14, 22));
        }
    }

    private void buildCompactLayout() {
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        column.setHgrow(Priority.ALWAYS);
        contentGrid.getColumnConstraints().add(column);

        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.NEVER);
            contentGrid.getRowConstraints().add(row);
        }

        addPanel(inputPanel, 0, 0);
        addPanel(chargeSquarePane, 0, 1);
        addPanel(formulaPanel, 0, 2);
        addPanel(resultPanel, 0, 3);
    }

    private void buildDesktopLayout(double width) {
        ColumnConstraints leftSpace = new ColumnConstraints();
        ColumnConstraints middle = new ColumnConstraints();
        ColumnConstraints right = new ColumnConstraints();

        if (width < 1400) {
            leftSpace.setPercentWidth(24);
            middle.setPercentWidth(36);
            right.setPercentWidth(40);
        } else {
            leftSpace.setPercentWidth(31);
            middle.setPercentWidth(30);
            right.setPercentWidth(39);
        }

        middle.setHgrow(Priority.ALWAYS);
        right.setHgrow(Priority.ALWAYS);
        contentGrid.getColumnConstraints().addAll(leftSpace, middle, right);

        RowConstraints top = new RowConstraints();
        top.setPercentHeight(58);
        top.setVgrow(Priority.ALWAYS);
        RowConstraints bottom = new RowConstraints();
        bottom.setPercentHeight(42);
        bottom.setVgrow(Priority.ALWAYS);
        contentGrid.getRowConstraints().addAll(top, bottom);

        VBox leftSpacer = new VBox();
        contentGrid.add(leftSpacer, 0, 0, 1, 2);
        addPanel(inputPanel, 1, 0);
        addPanel(chargeSquarePane, 2, 0);
        addPanel(formulaPanel, 1, 1);
        addPanel(resultPanel, 2, 1);
    }

    private void addPanel(javafx.scene.Node panel, int column, int row) {
        contentGrid.add(panel, column, row);
        GridPane.setHgrow(panel, Priority.ALWAYS);
        GridPane.setVgrow(panel, Priority.ALWAYS);
        if (panel instanceof javafx.scene.layout.Region region) {
            region.setMaxWidth(Double.MAX_VALUE);
        }
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
