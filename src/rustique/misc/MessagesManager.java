package rustique.misc;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessagesManager {

    /**
     * Constructor vacio de clase
     */
    public MessagesManager() {}

    /**
     * Muestra de dialogo de informacion
     * @param msg mensaje a mostrar en dialogo
     */
    public static void showInformationAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("");
        alert.setContentText(msg);
        fix(alert);
        alert.showAndWait();
    }

    /**
     * Muestra de dialogo de error
     * @param msg mensaje a mostrar en dialogo
     */
    public static void showErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.getDialogPane().setPrefWidth(140 + msg.length() * 5);
        fix(alert);
        alert.showAndWait();
    }

    /**
     * Muestra de dialogo de error fatal
     */
    public static void showFatalErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("");
        alert.setContentText("Se ha producido un error");
        fix(alert);
        alert.showAndWait();
    }

    /**
     * Muestra de dialogo de confirmacion
     * @param msg mensaje a mostrar en dialogo
     * @return true si se confirma la accion, de lo contrario false
     */
    public static boolean confirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("");
        alert.setContentText(msg);
        fix(alert);
        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }

    private static void fix(Alert alert) {
        Platform.runLater(() -> {
            alert.getDialogPane().getScene().getWindow().sizeToScene();
        });
    }
}
