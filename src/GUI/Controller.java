package GUI;

import BE.Genre;
import BE.Movie;
import BLL.GenreManager;
import DAL.DBManager;
import GUI.aboutView.AboutController;
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

import javax.sql.rowset.JdbcRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private Button btnDebug;

    public void debug(ActionEvent actionEvent)
    {
        DBManager dbMgr = new DBManager();

        java.util.Date date = new java.util.Date();

        try {

            ArrayList<Movie> movies = dbMgr.getAllMovies();
            Movie m = movies.get(2);
            dbMgr.addGenreToMovie(m, dbMgr.addGenre(new Genre("TestGenre")));

            Genre addedGenre = dbMgr.addGenre(new Genre("TestGenre"));

            addedGenre.setName("UpdatedGenre");
            dbMgr.updateGenre(addedGenre);

            dbMgr.deleteGenreByID(addedGenre.getId());

            Movie addedMovie = dbMgr.addMovie(new Movie("Test",2f,2f, "test",new java.sql.Timestamp(date.getTime()), new ArrayList<Genre>()));
            addedMovie.setTitle("UpdateMovie");
            dbMgr.updateMovie(addedMovie);

            dbMgr.deleteMovieByID(addedMovie.getId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void aboutClicked() {
        startAboutView("aboutView/AboutView.fxml");
    }
    public void dialogNewGenre() {
        startNewGenreView("newGenreView/NewGenreView.fxml");
    }

    public void startAboutView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            AboutController aboutController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void startNewGenreView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewGenreController newGenreController = loader.getController();
            //    newPlaylistController.setMainController(this);
            //    newPlaylistController.setManager(this.sM);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
