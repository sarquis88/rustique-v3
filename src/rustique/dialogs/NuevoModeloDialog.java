package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rustique.Main;
import rustique.controllers.TrabajosController;
import rustique.misc.MessagesManager;
import rustique.misc.RustiqueParameters;
import rustique.controllers.ClientesController;
import rustique.controllers.Controller;
import rustique.controllers.ObrasController;
import rustique.models.Cliente;
import rustique.models.Modelo;
import rustique.models.Obra;
import rustique.models.Trabajo;

public class NuevoModeloDialog extends RustiqueDialog implements RustiqueParameters {

    private Controller thisController;

    private TextField nombre;

    private TextField saldo;
    private TextArea comentarios;

    private TextField precio;
    private TextField autor;
    private TextField tipo;
    private TextField tamanio;

    private TextField cliente;
    private DatePicker datePicker;

    /**
     * Constructor de la clase
     * @param title titulo de la ventana
     */
    public NuevoModeloDialog(String title, Controller controller) {
        thisDialog = new Dialog<>();
        thisDialog.setTitle(title);
        thisDialog.setHeaderText("");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        thisController = controller;
        if(thisController instanceof ClientesController) {
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
        }
        else if(thisController instanceof ObrasController) {
            nombre = new TextField();
            nombre.setPromptText("Nombre");
            autor = new TextField();
            autor.setPromptText("Autor");
            tipo = new TextField();
            tipo.setPromptText("Tipo");
            tamanio = new TextField();
            tamanio.setPromptText("Tamaño");
            precio = new TextField();
            precio.setPromptText("Precio");

            grid.add(new Label("Nombre:"), 0, 0);
            grid.add(nombre, 1, 0);
            grid.add(new Label("Autor:"), 0, 1);
            grid.add(autor, 1, 1);
            grid.add(new Label("Tipo:"), 0, 2);
            grid.add(tipo, 1, 2);
            grid.add(new Label("Tamaño:"), 0, 3);
            grid.add(tamanio, 1, 3);
            grid.add(new Label("Precio:"), 0, 4);
            grid.add(precio, 1, 4);
        }
        else if(thisController instanceof TrabajosController) {
            cliente = new TextField();
            cliente.setPromptText("Cliente");
            comentarios = new TextArea();
            comentarios.setPromptText("Comentarios");
            comentarios.setPrefSize(120, 50);
            comentarios.setWrapText(true);
            datePicker = new DatePicker();
            datePicker.setEditable(false);

            grid.add(new Label("Cliente:"), 0, 0);
            grid.add(cliente, 1, 0);
            grid.add(new Label("Comentarios:"), 0, 1);
            grid.add(comentarios, 1, 1);
            grid.add(new Label("Fecha:"), 0, 2);
            grid.add(datePicker, 1, 2);
        }

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Retorna un modelo con los parametros ingresados en la ventana
     * @return objeto modelo
     */
    public Modelo getResult() {
        if(thisDialog.getResult() == ButtonType.OK) {
            if(thisController instanceof ClientesController) {
                Cliente cliente = new Cliente();
                cliente.setNombre(this.nombre.getText());

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
            else if(thisController instanceof ObrasController) {
                Obra obra = new Obra();
                obra.setNombre(nombre.getText());
                obra.setAutor(autor.getText());
                obra.setTipo(tipo.getText());
                obra.setTamanio(tamanio.getText());

                if(precio.getText() == null || !Main.isNumeroValido(precio.getText()))
                    obra.setPrecio(0);
                else
                    obra.setPrecio(Main.safeDecode(precio.getText()));
                return obra;
            }
            else if(thisController instanceof TrabajosController) {
                Trabajo trabajo = new Trabajo();
                trabajo.setCliente(cliente.getText());
                if(comentarios.getText() == null)
                    trabajo.setComentarios("");
                else if(comentarios.getText().length() > comentMaxSize) {
                    trabajo.setComentarios("");
                    MessagesManager.showInformationAlert("Comentario muy largo, " +
                            "se puso en blanco");
                }
                else
                    trabajo.setComentarios(this.comentarios.getText());

                if(datePicker.getValue() == null)
                    trabajo.setFecha("Sin fecha");
                else
                    trabajo.setFecha(datePicker.getValue().toString());
                return trabajo;
            }
        }
        return null;
    }
}
