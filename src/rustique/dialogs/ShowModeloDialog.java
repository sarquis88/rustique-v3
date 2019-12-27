package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import rustique.controllers.TrabajosController;
import rustique.misc.RustiqueParameters;
import rustique.controllers.ClientesController;
import rustique.controllers.Controller;
import rustique.controllers.ObrasController;
import rustique.models.Cliente;
import rustique.models.Modelo;
import rustique.models.Obra;
import rustique.models.Trabajo;

public class ShowModeloDialog extends RustiqueDialog implements RustiqueParameters {

    private Controller thisController;
    private ButtonType borrar;
    private ButtonType modificar;
    private ButtonType volver;
    private ButtonType verFoto;
    private ButtonType agregarFoto;
    private ButtonType comentarios;

    /**
     * Constructor de la clase
     * @param modelo modelo a mostrar
     */
    public ShowModeloDialog(Modelo modelo) {
        thisDialog = new Dialog<>();

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
                thisDialog.getDialogPane().getButtonTypes().addAll(verFoto);
            }
            else {
                agregarFoto = new ButtonType("Agregar foto");
                thisDialog.getDialogPane().getButtonTypes().addAll(agregarFoto);
            }
        }
        else if(modelo instanceof Trabajo) {
            thisController = TrabajosController.getInstance();
            titulo = "Ver Trabajo";

            comentarios = new ButtonType("Comentarios");
            thisDialog.getDialogPane().getButtonTypes().add(comentarios);
        }

        thisDialog.setTitle(titulo);
        thisDialog.setHeaderText("");

        borrar = new ButtonType("Borrar");
        modificar = new ButtonType("Modificar");
        volver = new ButtonType("Volver");
        thisDialog.getDialogPane().getButtonTypes().addAll(borrar, modificar, volver);

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

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra la ventana del dialogo e interpreta los botones apretados
     * por el usuario
     */
    public void show() {

        super.show();

        if(this.thisDialog.getResult() == this.volver)
            return;
        if(this.thisDialog.getResult() == this.modificar)
            thisController.actionPerformed("modificar");
        else if(this.thisDialog.getResult() == this.borrar)
            thisController.actionPerformed("borrar");
        else if(this.thisDialog.getResult() == this.verFoto)
            thisController.actionPerformed("ver-foto");
        else if(this.thisDialog.getResult() == this.agregarFoto)
            thisController.actionPerformed("agregar-foto");
        else if(this.thisDialog.getResult() == this.comentarios)
            thisController.actionPerformed("comentarios");
    }
}
