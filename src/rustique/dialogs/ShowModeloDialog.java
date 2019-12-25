package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import rustique.RustiqueParameters;
import rustique.controllers.ClientesController;
import rustique.controllers.Controller;
import rustique.controllers.ObrasController;
import rustique.models.Cliente;
import rustique.models.Modelo;
import rustique.models.Obra;

public class ShowModeloDialog implements RustiqueParameters {

    private Controller thisController;
    private Dialog<ButtonType> dialog;
    private ButtonType borrar;
    private ButtonType modificar;
    private ButtonType volver;
    private ButtonType verFoto;
    private ButtonType agregarFoto;

    /**
     * Constructor de la clase
     * @param modelo modelo a mostrar
     */
    public ShowModeloDialog(Modelo modelo) {

        dialog = new Dialog<>();

        String titulo = "";
        thisController = null;

        if(modelo instanceof Cliente) {
            thisController = ClientesController.getInstance();
            titulo = "Ver Cliente";
        }
        else if(modelo instanceof Obra) {
            thisController = ObrasController.getInstance();
            titulo = "Ver Obra";
            if(((Obra) modelo).getHasImage().equals("Si")) {
                verFoto = new ButtonType("Ver foto");
                dialog.getDialogPane().getButtonTypes().addAll(verFoto);
            }
            else {
                agregarFoto = new ButtonType("Agregar foto");
                dialog.getDialogPane().getButtonTypes().addAll(agregarFoto);
            }
        }

        dialog.setTitle(titulo);
        dialog.setHeaderText("");

        borrar = new ButtonType("Borrar");
        modificar = new ButtonType("Modificar");
        volver = new ButtonType("Volver");
        dialog.getDialogPane().getButtonTypes().addAll(borrar);
        dialog.getDialogPane().getButtonTypes().addAll(modificar);
        dialog.getDialogPane().getButtonTypes().addAll(volver);

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        for(int row = 0; row < modelo.getDatos().size(); row = row + 2) {

            Label tipoDato = new Label(modelo.getDatos().get(row));
            tipoDato.setStyle("-fx-font-weight: bold;");
            grid.add(tipoDato, 0, row);

            Label dato = new Label(modelo.getDatos().get(row + 1));
            grid.add(dato, 1, row);
        }

        dialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra la ventana del dialogo e interpreta los botones apretados
     * por el usuario
     */
    public void show() {

        Platform.runLater(() -> dialog.getDialogPane().getScene().getWindow().sizeToScene());

        dialog.showAndWait();

        if(this.dialog.getResult() == this.volver)
            return;
        if(this.dialog.getResult() == this.modificar)
            thisController.actionPerformed("modificar");
        else if(this.dialog.getResult() == this.borrar)
            thisController.actionPerformed("borrar");
        else if(this.dialog.getResult() == this.verFoto)
            thisController.actionPerformed("ver-foto");
        else if(this.dialog.getResult() == this.agregarFoto)
            thisController.actionPerformed("agregar-foto");
    }
}
