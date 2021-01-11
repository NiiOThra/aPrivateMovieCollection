package GUI.deleteGenreView;

import BE.Genre;
import BLL.GenreManager;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteGenreController {

    @FXML
    Button btnYes;

    @FXML
    Button btnNo;

    private Genre selectedGenre;

    private GenreManager gM;
    private GUI.Controller mainController;

    public void setManager(GenreManager sM) {
        this.gM = gM;
    }
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setSelectedGenre(Genre genre) {
        this.selectedGenre = genre;
    }

  /*  public void doDelete(ActionEvent actionEvent) {
        try {

            gM.deleteGenreByID(selectedGenre.getId());
            doClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent)
    {
        doClose();
    }

    private void doClose()
    {
        mainController.reloadGenreTable();
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }
*/
}
