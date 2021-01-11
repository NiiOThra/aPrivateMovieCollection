package GUI.newGenreView;

import BLL.GenreManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class NewGenreController {

    @FXML
    public Button cancelBttn;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtPlaylistName;

    private GUI.Controller mainController;

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    private void doClose(){

    }

    public void close(ActionEvent actionEvent) {
        doClose();
    }

}
