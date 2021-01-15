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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    public void updateEditMovieValues() {
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

    public void chooseFile(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("MPEG4", "*.mpeg4")
        );

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            txtFileLink.setText(file.getAbsolutePath());
            movieToEdit.setFileLink(file.getAbsolutePath());
        }

    }

    private String makeImdbUrl() {
        return "https://www.imdb.com/find?q=" + txtTitle.getText().replace(' ', '+');
    }

    public void save(ActionEvent actionEvent) {

        float imdbRating = -1;
        float myRating = -1;

        try {
            myRating = Float.parseFloat(txtMyRating.getText());
            imdbRating = Float.parseFloat(txtImdbRating.getText());
            updateEditMovieValues();

            if (imdbRating != -1 && myRating != -1 &&
                    imdbRating > 0 && imdbRating < 10 &&
                    myRating > 0 && myRating < 10) {
                doSave();
                close();
            } else {
                ShowErrorBox();
            }

        } catch (Exception e) {
            ShowErrorBox();
        }
    }

    private void ShowErrorBox() {
        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Ratings has be to between 1 - 10",
                ok);

        alert.setTitle("Rating error!");
        Optional<ButtonType> result = alert.showAndWait();
    }

    private void doSave() {
        try {
            if (movieToEdit.getId() == -1) {
                mMgr.add(movieToEdit);
            } else {
                mMgr.update(movieToEdit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
