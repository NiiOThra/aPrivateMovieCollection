package GUI;

import BE.Genre;
import BE.Movie;
import BLL.GenreManager;
import BLL.MovieManager;
import GUI.aboutView.AboutController;
import GUI.createUpdateMovieView.CreateEditMovieController;
import GUI.createUpdateGenreView.CreateEditGenreController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML
    private Button btnPlayMovieInVLC;
    @FXML
    private TextField txtFilter;
    @FXML
    private Button btnRefresh;
    @FXML
    private TableView genresTable;
    @FXML
    private TableColumn<Genre, String> genresColumnName;
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableColumn<Movie, String> titleColumn;
    @FXML
    private TableColumn<Movie, String> genresColumn;
    @FXML
    private TableColumn<Movie, String> myRatingColumn;
    @FXML
    private TableColumn<Movie, String> imdbRatingColumn;
    @FXML
    private TableColumn<Movie, String> lastViewColumn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button newGenresBtn;
    @FXML
    private Button btnDebug;
    @FXML
    private Button btnAddMovie;
    @FXML
    private Button btnEditMovie;
    @FXML
    private Button btnDeleteMovie;
    @FXML
    private Button btnNewGenre;
    @FXML
    private Button btnEditGenre;
    @FXML
    private Button btnDeleteGenre;


    private ObservableList<Movie> MovieList;
    private ObservableList<Genre> GenreList;


    private static final MovieManager mMgr = MovieManager.getInstance();
    private static final GenreManager gMgr = GenreManager.getInstance();

    public void reloadGenresTable() {
        try {
            GenreList = FXCollections.observableList(gMgr.getAllGenres());

            int index = genresTable.getSelectionModel().getFocusedIndex();
            this.genresTable.setItems(GenreList);

            genresColumnName.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            genresTable.getSelectionModel().select(index);
        } catch (Exception exception) {
            System.out.println("No genres loaded");
        }
    }

    public void reloadMoviesTable() {
        try {
            MovieList = FXCollections.observableList(mMgr.getAllMovies());

            int index = moviesTable.getSelectionModel().getFocusedIndex();
            //this.moviesTable.setItems(MovieList);

            titleColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            genresColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getGenresString()));
            myRatingColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getMyRatingString()));
            imdbRatingColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getImdbRatingString()));
            lastViewColumn.cellValueFactoryProperty().setValue(cellData -> new SimpleStringProperty(cellData.getValue().getLastView().toString()));


            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Movie> filteredData = new FilteredList<>(MovieList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(movie -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    float floatFilter = -1;
                    // Checks if new search string doesn't contain characters.
                    if (!newValue.contains("[a-zA-Z]+")) {
                        try {
                            float parsed = Float.parseFloat(newValue);
                            floatFilter = parsed;
                        } catch (Exception e) {
                        }
                    }
                    if (movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches title.
                    } else if (movie.getGenresString().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches a genre.
                    } else if (movie.getLastView().toString().contains(lowerCaseFilter)) {
                        return true; // Filter matches a genre.
                    } else if (floatFilter != -1) {
                        if (movie.getMyRating() >= Float.parseFloat(lowerCaseFilter)) {
                            return true; // Filter matches my rating.
                        }
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Movie> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(this.moviesTable.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            this.moviesTable.setItems(sortedData);


            moviesTable.getSelectionModel().select(index);
        } catch (Exception exception) {
            System.out.println("No movies loaded");
        }
    }

    public void playMovie(ActionEvent actionEvent) {
        try {
            mMgr.playMovie(moviesTable.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshUI() {
        reloadGenresTable();
        reloadMoviesTable();
    }

    public void debug(ActionEvent actionEvent) {

        java.util.Date date = new java.util.Date();

        try {
            refreshUI();

            var expiredMovies = mMgr.getExpiredMovies();

            Movie movie = mMgr.getById(3);
            String test = movie.getGenresString();
            List<Movie> movies = mMgr.getAllMovies();
            //gMgr.addGenreToMovie(gMgr.add("TestGenre"),m);

            // Genre addedGenre = gMgr.add("TestGenre");

            //addedGenre.setName("UpdatedGenre");
            // gMgr.update(addedGenre);

            // gMgr.delete(addedGenre);

            //Movie addedMovie = mMgr.add(new Movie("Test",2f,2f, "test",new java.sql.Timestamp(date.getTime()), new ArrayList<Genre>()));
            // addedMovie.setTitle("UpdateMovie");
            // mMgr.update(addedMovie);

            // mMgr.delete(addedMovie);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public void filterMovies() {
        //FilteredList<Movie> filteredList = MovieList.filtered(x -> x.getTitle().toLowerCase().contains(txtFilter.getText().toLowerCase()) || x.getGenresString().toLowerCase().contains(txtFilter.getText().toLowerCase()));

        //this.moviesTable.setItems(filteredList);
        //filteredList.sorted().comparatorProperty().bind(this.moviesTable.comparatorProperty());

    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void aboutClicked() {
        startAboutView("aboutView/AboutView.fxml");
    }

    public void dialogNewGenre() {
        startNewGenreView("createUpdateGenreView/CreateEditGenreView.fxml");
    }

    public void dialogEditGenre() {
        var genreToUpdate = (Genre) genresTable.getSelectionModel().getSelectedItem();
        if (genreToUpdate != null) {
            startEditGenreView("createUpdateGenreView/CreateEditGenreView.fxml", genreToUpdate);
        }
    }

    public void dialogDeleteGenre() {
        var itemToDelete = (Genre) genresTable.getSelectionModel().getSelectedItem();
        boolean shouldDelete = ShowDeletionConfirmationBox("Genre");
        if (shouldDelete) {
            try {
                gMgr.delete(itemToDelete);
                refreshUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dialogNewMovie() {
        startNewMovieView("createUpdateMovieView/CreateEditMovieView.fxml");
    }

    public void dialogEditMovie() {
        var movieToUpdate = (Movie) moviesTable.getSelectionModel().getSelectedItem();
        if (movieToUpdate != null) {
            startEditMovieView("createUpdateMovieView/CreateEditMovieView.fxml", movieToUpdate);
        }
    }

    public void dialogDeleteMovie() {
        var itemToDelete = (Movie) moviesTable.getSelectionModel().getSelectedItem();
        boolean shouldDelete = ShowDeletionConfirmationBox("Movie");
        if (shouldDelete) {
            try {
                mMgr.delete(itemToDelete);
                refreshUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ShowExpiredMoviesBox() {
        try {
            var expiredMovies = mMgr.getExpiredMovies();

            String moviesString = "";

            for (Movie m : expiredMovies) {
                moviesString += m.toString() + "\n";
            }

            ButtonType yes = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    moviesString,
                    yes);

            alert.setTitle(expiredMovies.stream().count() + " expired movies!");
            Optional<ButtonType> result = alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean ShowDeletionConfirmationBox(String type) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Are you sure you want to delete: " + type + ".",
                yes,
                no);

        alert.setTitle(type + " deletion warning!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            return true;
        }
        return false;
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

    public void startNewMovieView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            CreateEditMovieController createEditMovieController = loader.getController();
            createEditMovieController.setNewMovie();
            createEditMovieController.reloadGenresTable();
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startEditMovieView(String fxmlPath, Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            CreateEditMovieController createEditMovieController = loader.getController();
            createEditMovieController.setMovieToEdit(movie);
            createEditMovieController.reloadGenresTable();
            //    newPlaylistController.setManager(this.sM);
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
            CreateEditGenreController createEditGenreController = loader.getController();
            createEditGenreController.setNewGenre();
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startEditGenreView(String fxmlPath, Genre genre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            CreateEditGenreController createEditGenreController = loader.getController();
            createEditGenreController.setGenreToEdit(genre);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
