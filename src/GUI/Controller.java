package GUI;

import BLL.GenreManager;
import GUI.newGenreView.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button btnPlayVLC;

    @FXML
    private TextField txtFilter;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<?> genresTable;

    @FXML
    private TableColumn<?, ?> playlistNameColumn;

    @FXML
    private TableView<?> moviesInGenresTable;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> genresColumn;

    @FXML
    private TableColumn<?, ?> ratingColumn;

    @FXML
    private TableColumn<?, ?> durationColumn;

    @FXML
    private Button closeBtn;

    @FXML
    private Button newGenresBtn;



    private static final GenreManager gM = GenreManager.getInstance();






    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void dialogNewGenre(ActionEvent actionEvent) {
        startNewGenreView("NewGenreView.fxml");
    }














    public void startNewGenreView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewGenreController newPlaylistController = loader.getController();
            newPlaylistController.setMainController(this);
            newPlaylistController.setManager(this.gM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
