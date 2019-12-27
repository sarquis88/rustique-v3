package rustique.dialogs;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import rustique.misc.ImagesManager;
import rustique.misc.RustiqueParameters;

public class NuevaImagenDialog extends RustiqueDialog implements RustiqueParameters {

    /**
     * Constructor de clase
     */
    public NuevaImagenDialog() {
        thisDialog = new Dialog<>();
        thisDialog.setTitle("Agregar imagen");
        thisDialog.setHeaderText("");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    /**
     * Muestra de dialogo
     * @param imgNombre nombre de imagen a agregar
     * @param change true si es un cambio, false si es una obra nueva
     * @return "Si" si se agrego la foto, de lo contrario "No"
     */
    public String show(boolean change, String imgNombre) {
        String hasImage = "No";

        Image img = ImagesManager.chooseImage();
        if(img == null)
            return hasImage;

        ImageView imgView = ImagesManager.scale(img);

        GridPane grid = new GridPane();
        grid.setMaxWidth(imgView.getFitWidth());
        grid.setMaxHeight(imgView.getFitHeight());

        grid.add(imgView, 0, 0);

        thisDialog.getDialogPane().getChildren().remove(grid);
        thisDialog.getDialogPane().setContent(grid);

        super.show();

        if(this.thisDialog.getResult() == ButtonType.OK) {
            hasImage = "Si";

            // si se está haciendo un cambio de imagen, se borra la
            // imagen vieja y luego se escribe la nueva
            if(change)
                ImagesManager.removeImage(imgNombre);

            ImagesManager.writeImage(img, imgNombre);
        }
        return hasImage;
    }
}
