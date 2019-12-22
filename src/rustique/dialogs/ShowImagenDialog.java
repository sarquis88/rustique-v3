package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import rustique.ImagesManager;
import rustique.MessagesManager;
import rustique.controllers.ObrasController;

public class ShowImagenDialog {

    private static ObrasController thisController;

    private Dialog<ButtonType> dialog;
    private GridPane grid;
    private ButtonType cambiar;
    private ButtonType borrar;

    /**
     * Constructor de clase
     */
    public ShowImagenDialog() {
        thisController = ObrasController.getInstance();

        dialog = new Dialog<>();
        dialog.setTitle("Ver imagen");
        dialog.setHeaderText("");

        this.cambiar = new ButtonType("Cambiar");
        this.borrar = new ButtonType("Borrar");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,
                cambiar, borrar);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        dialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra imagen en pantalla
     * @param imageID -1 si se quiere elegir una imagen desde memoria,
     * o el id de una imagen en particular para mostrar dicha imagen
     */
    public void show(int imageID) {
        Image img;

        if(imageID == -1) {
            this.dialog.getDialogPane().getButtonTypes().removeAll(
                    this.cambiar, this.borrar);
            img = ImagesManager.chooseImage();
            if (img == null)
                return;
        }
        else {
            this.dialog.setTitle(thisController.getObraNameById(imageID));
            img = new Image("file:" + ImagesManager.getObrasPath() + imageID + "." + "png");
            if(img.getHeight() == 0)
                img = new Image("file:" + ImagesManager.getObrasPath() + imageID + "." + "jpg");
        }

        ImageView imgView = ImagesManager.scale(img);

        grid.setMaxWidth(imgView.getFitWidth());
        grid.setMaxHeight(imgView.getFitHeight());

        grid.add(imgView, 0, 0);

        Platform.runLater(() -> dialog.getDialogPane().getScene().getWindow().sizeToScene());

        this.dialog.showAndWait();


        if(this.dialog.getResult() == this.cambiar) {
            NuevaImagenDialog nuevaImagenDialog = new NuevaImagenDialog();
            nuevaImagenDialog.show(imageID, true);
        }
        else if(this.dialog.getResult() == this.borrar) {
            if(MessagesManager.confirmation("Seguro desea borrar la imagen?"))
                thisController.actionPerformed("borrar-foto");
        }
    }
}
