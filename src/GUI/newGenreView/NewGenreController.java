package GUI.newGenreView;

import BLL.GenreManager;
import BLL.MovieManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewGenreController {

    @FXML
    public Button cancelBtn;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtGenreName;

    private GUI.Controller mainController;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    private void doClose(){


    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

}
