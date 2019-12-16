package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rustique.Main;
import rustique.MessagesManager;
import rustique.RustiqueParameters;
import rustique.models.Cliente;

public class CambiarClienteDialog implements RustiqueParameters {

    private Dialog<ButtonType> dialog;
    private TextField nombre;
    private TextField saldo;
    private TextArea comentarios;
    private Cliente viejoCliente;

    /**
     * Constructor de la clase
     * @param viejo cliente a modificar
     */
    public CambiarClienteDialog(Cliente viejo) {
        this.viejoCliente = viejo;
        dialog = new Dialog<>();
        dialog.setTitle("Cambiar cliente");
        dialog.setHeaderText("");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nombre = new TextField();
        nombre.setPromptText("Nombre");
        nombre.setText(viejoCliente.getNombre());
        saldo = new TextField();
        saldo.setPromptText("Saldo");
        saldo.setText(String.valueOf(viejoCliente.getSaldo()));

        comentarios = new TextArea();
        comentarios.setPromptText("Comentarios");
        comentarios.setText(viejoCliente.getComentarios());
        comentarios.setPrefSize(120, 50);
        comentarios.setWrapText(true);

        Label nombreActual = new Label("Nombre actual: ");
        nombreActual.setStyle("-fx-font-weight: bold;");

        Label saldoActual = new Label("Saldo actual: ");
        saldoActual.setStyle("-fx-font-weight: bold;");

        Label comentActual = new Label("Comentario actual: ");
        comentActual.setStyle("-fx-font-weight: bold;");

        Label nombreNuevo = new Label("Nombre nuevo: ");
        nombreNuevo.setStyle("-fx-font-weight: bold;");

        Label saldoNuevo = new Label("Saldo nuevo: ");
        saldoNuevo.setStyle("-fx-font-weight: bold;");

        Label comentNuevo = new Label("Comentario nuevo: ");
        comentNuevo.setStyle("-fx-font-weight: bold;");

        grid.add(nombreActual, 0, 0);
        grid.add(new Label(viejo.getNombre()), 1, 0);
        grid.add(saldoActual, 0, 1);
        grid.add(new Label(String.valueOf(viejo.getSaldo())), 1, 1);
        grid.add(comentActual, 0, 2);
        grid.add(new Label(viejo.getComentarios()), 1, 2);

        grid.add(nombreNuevo, 8, 0);
        grid.add(nombre, 9, 0);
        grid.add(saldoNuevo, 8, 1);
        grid.add(saldo, 9, 1);
        grid.add(comentNuevo, 8, 2);
        grid.add(comentarios, 9, 2);

        dialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra de dialogo
     */
    public void show() {
        Platform.runLater(() -> {
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        this.dialog.showAndWait();
    }

    /**
     * Retorno de nuevo cliente cambiado
     * @return objeto Cliente nuevo
     */
    public Cliente getResult() {
        Cliente cliente = new Cliente();
        if(dialog.getResult() == ButtonType.OK) {
            cliente.setNombre(this.nombre.getText());
            cliente.setId(viejoCliente.getId());

            if(this.saldo.getText() == null || !Main.isNumeroValido(this.saldo.getText()))
                cliente.setSaldo(0);
            else
                cliente.setSaldo(Main.safeDecode(this.saldo.getText()));

            if(comentarios.getText() == null)
                cliente.setComentarios("");
            else if(comentarios.getText().length() > comentMaxSize) {
                cliente.setComentarios("");
                MessagesManager.showInformationAlert("Comentario muy largo, " +
                        "se puso en blanco");
            }
            else
                cliente.setComentarios(this.comentarios.getText());

            return cliente;
        }
        return null;
    }
}
