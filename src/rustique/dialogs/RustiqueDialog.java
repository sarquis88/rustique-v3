package rustique.dialogs;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RustiqueDialog {

    protected Dialog<ButtonType> thisDialog;

    /**
     * Muestra de ventana
     */
    public void show() {

        Stage stage = (Stage) thisDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:./src/images/logo.png")); // To add an icon

        Platform.runLater(() -> thisDialog.getDialogPane().getScene().getWindow().sizeToScene());
        thisDialog.showAndWait();
    }
}
