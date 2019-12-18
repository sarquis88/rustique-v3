package rustique.dialogs;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import rustique.ImagesManager;

public class NuevaImagenDialog {


    private Dialog<ButtonType> dialog;

    /**
     * Constructor de clase
     */
    public NuevaImagenDialog() {
        dialog = new Dialog<>();
        dialog.setTitle("Agregar imagen");
        dialog.setHeaderText("");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    /**
     * Muestra de dialogo
     * @param idGlobal id de imagen a agregar
     * @param change true si es un cambio, false si es una obra nueva
     * @return "Si" si se agrego la foto, de lo contrario "No"
     */
    public String show(int idGlobal, boolean change) {
        String hasImage = "No";

        Image img = ImagesManager.chooseImage();
        if(img == null)
            return hasImage;

        ImageView imgView = ImagesManager.scale(img);

        GridPane grid = new GridPane();
        grid.setMaxWidth(imgView.getFitWidth());
        grid.setMaxHeight(imgView.getFitHeight());

        grid.add(imgView, 0, 0);

        dialog.getDialogPane().getChildren().remove(grid);
        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> {
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        dialog.showAndWait();

        if(this.dialog.getResult() == ButtonType.OK) {
            hasImage = "Si";

            // si se está haciendo un cambio de imagen, se borra la
            // imagen vieja y luego se escribe la nueva
            if(change)
                ImagesManager.removeImage(idGlobal);

            ImagesManager.writeImage(img, idGlobal);
        }
        return hasImage;
    }
}
