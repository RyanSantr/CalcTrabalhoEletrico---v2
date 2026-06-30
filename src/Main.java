import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.MainView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 1600, 900);

        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("TRABALHO ELETRICO - Montagem de Cargas");
        stage.setMinWidth(1280);
        stage.setMinHeight(780);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
