package sample;

import GUI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Main.
 */
public class Main extends Application {

    private final static String APP_TITLE = "Private Movie Collection";
    private final static int APP_WIDTH = 1200;
    private final static int APP_HEIGHT = 580;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.ShowExpiredMoviesBox();
        controller.refreshUI();
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(new Scene(root, APP_WIDTH, APP_HEIGHT));
        primaryStage.show();
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
