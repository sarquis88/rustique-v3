package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import rustique.controllers.ClientesController;
import rustique.models.Cliente;

public class ShowClienteDialog {

    private ClientesController thisController;

    private Dialog<ButtonType> dialog;
    private ButtonType borrar;
    private ButtonType modificar;
    private ButtonType volver;

    /**
     * Constructor de la clase
     * @param cliente cliente a mostrar
     */
    public ShowClienteDialog(Cliente cliente) {
        thisController = ClientesController.getInstance();

        dialog = new Dialog<>();
        dialog.setTitle("Ver cliente");
        dialog.setHeaderText("");

        borrar = new ButtonType("Borrar");
        modificar = new ButtonType("Modificar");
        volver = new ButtonType("Volver");
        dialog.getDialogPane().getButtonTypes().addAll(borrar);
        dialog.getDialogPane().getButtonTypes().addAll(modificar);
        dialog.getDialogPane().getButtonTypes().addAll(volver);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label pregunta = new Label("Desea realizar algun cambio?");
        pregunta.setStyle("-fx-font-weight: bold;");

        Label nombreLabel = new Label("Nombre: ");
        nombreLabel.setStyle("-fx-font-weight: bold;");

        Label saldoLabel = new Label("Saldo: ");
        saldoLabel.setStyle("-fx-font-weight: bold;");

        Label idLabel = new Label("ID: ");
        idLabel.setStyle("-fx-font-weight: bold;");

        Label comentariosLabel = new Label("Comentarios: ");
        comentariosLabel.setStyle("-fx-font-weight: bold;");

        Label nombre = new Label(cliente.getNombre());
        Label saldo = new Label(String.valueOf(cliente.getSaldo()));
        Label id = new Label(String.valueOf(cliente.getId()));
        Label comentarios = new Label(cliente.getComentarios());

        grid.add(nombreLabel, 0, 0);
        grid.add(saldoLabel, 0, 1);
        grid.add(idLabel, 0, 2);
        grid.add(comentariosLabel, 0, 3);
        grid.add(nombre, 1, 0);
        grid.add(saldo, 1, 1);
        grid.add(id, 1, 2);
        grid.add(comentarios, 1, 3);

        grid.add(pregunta, 0, 5);

        dialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra la ventana del dialogo e interpreta los botones apretados
     * por el usuario
     */
    public void show() {

        Platform.runLater(() -> {
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        dialog.showAndWait();

        if(this.dialog.getResult() == this.volver)
            return;
        if(this.dialog.getResult() == this.modificar)
            thisController.actionPerformed("modificar-cliente");
        else if(this.dialog.getResult() == this.borrar)
            thisController.actionPerformed("borrar-cliente");
    }
}
