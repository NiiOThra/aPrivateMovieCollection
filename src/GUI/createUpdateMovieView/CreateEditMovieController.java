package GUI.createUpdateMovieView;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class CreateEditMovieController {
    @FXML
    public Label lblTop;
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

    private Movie movieToEdit;

    public void setNewMovie() {
        setup(true);
    }

    public void setMovieToEdit(Movie movie) {
        this.movieToEdit = movie;
        txtTitle.setText(movieToEdit.getTitle());
        txtImdbRating.setText(String.valueOf(movieToEdit.getImdbRating()));
        txtMyRating.setText(String.valueOf(movieToEdit.getMyRating()));
        txtFileLink.setText(movieToEdit.getFileLink());
        setup(false);
    }

    public void updateEditMovie() {
        this.movieToEdit.setTitle(txtTitle.getText());
        this.movieToEdit.setImdbRating(Float.parseFloat(txtImdbRating.getText()));
        this.movieToEdit.setMyRating(Float.parseFloat(txtMyRating.getText()));
        this.movieToEdit.setGenres((List<Genre>) genreTable.getSelectionModel().getSelectedItems());
        this.movieToEdit.setFileLink(txtFileLink.getText());
    }

    private void setup(boolean isNew) {
        if (isNew) {
            setTopLabel("New movie");
            movieToEdit = new Movie();
        } else {
            setTopLabel("Edit movie");

        }
    }

    private void setTopLabel(String newLabel) {
        this.lblTop.setText(newLabel);
    }

    public void reloadGenresTable() {
        try {
            GenreList = FXCollections.observableList(gMgr.getAllGenres());

            titleColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            this.genreTable.setItems(GenreList);

            genreTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            for (var genreInMovie : movieToEdit.getGenres()) {
                genreTable.getSelectionModel().select(genreInMovie);
            }


        } catch (Exception exception) {
            System.out.println("No genres loaded");
        }
    }

    public void searchImdb(ActionEvent actionEvent) {
        try {
            URI uri = new URI(makeImdbUrl());
            Desktop dt = Desktop.getDesktop();
            dt.browse(uri);
        } catch (Exception ex) {
        }
    }

    private String makeImdbUrl() {
        return "https://www.imdb.com/find?q=" + txtTitle.getText().replace(' ', '+');
    }

    public void save(ActionEvent actionEvent) {
        try {
            if (movieToEdit.getId() == -1) {
                updateEditMovie();
                mMgr.add(movieToEdit);
            } else {
                updateEditMovie();
                mMgr.update(movieToEdit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    public void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
