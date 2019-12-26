package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import rustique.misc.ImagesManager;
import rustique.misc.MessagesManager;
import rustique.misc.RustiqueParameters;
import rustique.controllers.ObrasController;

public class ShowImagenDialog extends RustiqueDialog implements RustiqueParameters {

    private static ObrasController thisController;

    private GridPane grid;
    private ButtonType cambiar;
    private ButtonType borrar;

    /**
     * Constructor de clase
     */
    public ShowImagenDialog() {
        thisController = ObrasController.getInstance();

        thisDialog = new Dialog<>();
        thisDialog.setTitle("Ver imagen");
        thisDialog.setHeaderText("");

        this.cambiar = new ButtonType("Cambiar");
        this.borrar = new ButtonType("Borrar");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,
                cambiar, borrar);

        grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra imagen en pantalla
     * @param imageID -1 si se quiere elegir una imagen desde memoria,
     * o el id de una imagen en particular para mostrar dicha imagen
     */
    public void show(int imageID) {
        Image img;

        if(imageID == -1) {
            this.thisDialog.getDialogPane().getButtonTypes().removeAll(
                    this.cambiar, this.borrar);
            img = ImagesManager.chooseImage();
            if (img == null)
                return;
        }
        else {
            this.thisDialog.setTitle(thisController.getObraNameById(imageID));
            img = new Image("file:" + ImagesManager.getObrasPath() + imageID + "." + "png");
            if(img.getHeight() == 0)
                img = new Image("file:" + ImagesManager.getObrasPath() + imageID + "." + "jpg");
        }

        ImageView imgView = ImagesManager.scale(img);

        grid.setMaxWidth(imgView.getFitWidth());
        grid.setMaxHeight(imgView.getFitHeight());

        grid.add(imgView, 0, 0);

        super.show();

        if(this.thisDialog.getResult() == this.cambiar) {
            NuevaImagenDialog nuevaImagenDialog = new NuevaImagenDialog();
            nuevaImagenDialog.show(imageID, true);
        }
        else if(this.thisDialog.getResult() == this.borrar) {
            if(MessagesManager.confirmation("Seguro desea borrar la imagen?"))
                thisController.actionPerformed("borrar-foto");
        }
    }
}
