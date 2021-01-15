package GUI.createUpdateGenreView;

import BE.Genre;
import BLL.GenreManager;
import GUI.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CreateEditGenreController {

    @FXML
    private Text lblTop;
    @FXML
    private TextField txtGenreName;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button btnSave;

    private Genre genreToEdit;

    private static final GenreManager gMgr = GenreManager.getInstance();

    private Controller mainController;


    public void setMainController(Controller controller) {
        this.mainController = controller;
    }

    public void setNewGenre() {
        setup(true);
    }

    public void setGenreToEdit(Genre genre) {
        this.genreToEdit = genre;
        txtGenreName.setText(genreToEdit.getName());
        setup(false);
    }

    private void setup(boolean isNew) {
        if (isNew) {
            setTopLabel("New genre");
            genreToEdit = new Genre();
        } else {
            setTopLabel("Edit genre");
        }
    }

    private void setTopLabel(String newLabel) {
        this.lblTop.setText(newLabel);
    }

    public void updateEditMovieValues() {
        this.genreToEdit.setName(txtGenreName.getText());
    }

    public void save(){
        updateEditMovieValues();
        doSave();
        close();
    }

    public void doSave(){
        try {
            if (genreToEdit.getId() == -1) {
                gMgr.add(genreToEdit);
            } else {
                gMgr.update(genreToEdit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        mainController.refreshUI();
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
