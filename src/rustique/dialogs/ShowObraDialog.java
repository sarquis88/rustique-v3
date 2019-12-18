package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import rustique.controllers.ObrasController;
import rustique.models.Obra;

public class ShowObraDialog {

    private Dialog<ButtonType> dialog;
    private ButtonType borrar;
    private ButtonType modificar;
    private ButtonType foto;
    private ButtonType agregarFoto;
    private ButtonType volver;
    private Obra obra;

    /**
     * Constructor de la clase
     * @param obra obra a mostrar
     */
    public ShowObraDialog(Obra obra) {
        this.obra = obra;

        dialog = new Dialog<>();
        dialog.setTitle("Ver obra");
        dialog.setHeaderText("");

        borrar = new ButtonType("Borrar");
        modificar = new ButtonType("Modificar");
        volver = new ButtonType("Volver");
        dialog.getDialogPane().getButtonTypes().addAll(borrar, modificar);

        if(obra.getHasImage().equalsIgnoreCase("Si")) {
            foto = new ButtonType("Foto");
            dialog.getDialogPane().getButtonTypes().add(foto);
        }
        else {
            agregarFoto = new ButtonType("Agregar foto");
            dialog.getDialogPane().getButtonTypes().add(agregarFoto);
            dialog.setWidth(500);
        }

        dialog.getDialogPane().getButtonTypes().addAll(volver);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label nombreLabel = new Label("Nombre: ");
        nombreLabel.setStyle("-fx-font-weight: bold;");

        Label autorLabel = new Label("Autor: ");
        autorLabel.setStyle("-fx-font-weight: bold;");

        Label precioLabel = new Label("Precio: ");
        precioLabel.setStyle("-fx-font-weight: bold;");

        Label tipoLabel = new Label("Tipo: ");
        tipoLabel.setStyle("-fx-font-weight: bold;");

        Label tamanioLabel = new Label("Tamaño: ");
        tamanioLabel.setStyle("-fx-font-weight: bold;");

        Label idLabel = new Label("ID: ");
        idLabel.setStyle("-fx-font-weight: bold;");

        Label nombre = new Label(obra.getNombre());
        Label autor = new Label(obra.getAutor());
        Label precio = new Label(String.valueOf(obra.getPrecio()));
        Label tipo = new Label(obra.getTipo());
        Label tamanio = new Label(obra.getTamanio());
        Label id = new Label(String.valueOf(obra.getId()));

        Label pregunta = new Label("Desea realizar algun cambio?");
        pregunta.setStyle("-fx-font-weight: bold;");

        grid.add(nombreLabel, 0, 0);
        grid.add(autorLabel, 0, 1);
        grid.add(precioLabel, 0, 2);
        grid.add(tipoLabel, 0, 3);
        grid.add(tamanioLabel, 0, 4);
        grid.add(idLabel, 0, 5);
        grid.add(nombre, 1, 0);
        grid.add(autor, 1, 1);
        grid.add(precio, 1, 2);
        grid.add(tipo, 1, 3);
        grid.add(tamanio, 1, 4);
        grid.add(id, 1, 5);

        grid.add(pregunta, 0, 9);

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

        if(this.dialog.getResult() == this.modificar)
            ObrasController.getInstance().actionPerformed("modificar-obra");
        else if(this.dialog.getResult() == this.borrar)
            ObrasController.getInstance().actionPerformed("borrar-obra");
        else if(this.dialog.getResult() == this.foto)
            ObrasController.getInstance().actionPerformed("ver-foto-clickeada");
        else if(this.dialog.getResult() == this.agregarFoto)
            ObrasController.getInstance().actionPerformed("agregar-foto");
    }
}
