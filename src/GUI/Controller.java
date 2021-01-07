package GUI;

import BLL.GenreManager;
import GUI.newGenreView.NewGenreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

    @FXML
    public Button closeBtn;

    @FXML
    public Button newGenreBtn;
/*
    @FXML
    private Button btnPlaySong;

    @FXML
    private TextField txtFilter;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<?> playlistTable;

    @FXML
    private TableColumn<?, ?> playlistNameColumn;

    @FXML
    private TableView<?> songsOnPlaylistTable;

    @FXML
    private TableColumn<?, ?> playlistSongsColumn;

    @FXML
    private TableColumn<?, ?> playlistSongsColumn1;

    @FXML
    private TableColumn<?, ?> playlistSongsColumn11;

    @FXML
    private TableView<?> songsTable;

    @FXML
    private TableColumn<?, ?> songTableTitleColumn;

    @FXML
    private TableColumn<?, ?> songTableArtistColumn;

    @FXML
    private TableColumn<?, ?> songTableCategoryColumn;

 */
private static final GenreManager gM = GenreManager.getInstance();
    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }




    public void startNewSongView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewGenreController newGenreController = loader.getController();
            newGenreController.setMainController(this);
            newGenreController.setManager(this.gM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
