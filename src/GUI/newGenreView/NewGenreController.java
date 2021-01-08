package GUI.newGenreView;

import BLL.GenreManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewGenreController {


    private GUI.Controller mainController;
    private GenreManager gM;


    @FXML
    private TextField txtGenre;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    public void setManager(GenreManager gM) {
        this.gM = gM;
    }
    @FXML
    void cancel(ActionEvent event) {
        doClose();
    }
    private void doClose()
    {
       // mainController.reloadPlaylistsTable();
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {

    }




    public NewGenreController(Controller mainController) {
        this.mainController = mainController;
    }


    public void setMainController(Controller controller) {
    }
}
