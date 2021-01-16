package GUI.aboutView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The type About controller.
 */
public class AboutController {

    /**
     * The Btn close.
     */
    @FXML
    Button btnClose;

    /**
     * Close.
     *
     * @param actionEvent the action event
     */
    public void close(ActionEvent actionEvent) {

        doClose();
    }

    private void doClose()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
