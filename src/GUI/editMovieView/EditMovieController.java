package GUI.editMovieView;

import BE.Genre;
import BE.Movie;
import BLL.GenreManager;
import BLL.MovieManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditMovieController {

    @FXML
    private Button btnSearchImdb;
    @FXML
    private Button btnChooseFile;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtMyRating;
    @FXML
    private TextField txtImdbRating;
    @FXML
    private TextField txtFileLink;

    @FXML
    private TableView<Genre> genreTable;

    @FXML
    private TableColumn<Genre, String> titleColumn;

    private ObservableList<Genre> GenreList;

    private static final MovieManager mMgr = MovieManager.getInstance();
    private static final GenreManager gMgr = GenreManager.getInstance();


    public void reloadGenresTable() {
        try {
            GenreList = FXCollections.observableList(gMgr.getAllGenres());

            this.genreTable.setItems(GenreList);

            genreTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            titleColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        } catch (Exception exception) {
            System.out.println("No genres loaded");
        }
    }
    public void searchImdb(ActionEvent actionEvent) {

    }
    public void save(ActionEvent actionEvent) {

    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
