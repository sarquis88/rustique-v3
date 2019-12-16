package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rustique.Main;
import rustique.MessagesManager;
import rustique.models.Cliente;

public class NuevoClienteDialog {

    private Dialog<ButtonType> dialog;
    private TextField nombre;
    private TextField saldo;
    private TextArea comentarios;

    /**
     * Constructor de la clase
     * @param title titulo de la ventana
     */
    public NuevoClienteDialog(String title) {
        dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nombre = new TextField();
        nombre.setPromptText("Nombre");
        saldo = new TextField();
        saldo.setPromptText("Saldo");

        comentarios = new TextArea();
        comentarios.setPromptText("Comentarios");
        comentarios.setPrefSize(120, 50);
        comentarios.setWrapText(true);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);
        grid.add(new Label("Saldo:"), 0, 1);
        grid.add(saldo, 1, 1);
        grid.add(new Label("Comentarios:"), 0, 4);
        grid.add(comentarios, 1, 4);

        dialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra de ventana
     */
    public void show() {
        Platform.runLater(() -> {
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        dialog.showAndWait();
    }

    /**
     * Retorna un cliente con los parametros ingresados en la ventana
     * @return objeto cliente
     */
    public Cliente getResult() {
        Cliente cliente = new Cliente();
        if(dialog.getResult() == ButtonType.OK) {
            cliente.setNombre(this.nombre.getText());

            if(this.saldo.getText() == null || !Main.isNumeroValido(this.saldo.getText()))
                cliente.setSaldo(0);
            else
                cliente.setSaldo(Main.safeDecode(this.saldo.getText()));

            int comentMaxSize = 40;
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
