package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import rustique.Main;
import rustique.models.Obra;

public class CambiarObraDialog {

    private Dialog<ButtonType> dialog;
    private TextField nombre;
    private TextField precio;
    private TextField autor;
    private TextField tipo;
    private TextField tamanio;
    private Obra viejaObra;

    /**
     * Constructor de la clase
     * @param vieja obra a modificar
     */
    public CambiarObraDialog(Obra vieja) {
        this.viejaObra = vieja;
        dialog = new Dialog<>();
        dialog.setTitle("Cambiar obra");
        dialog.setHeaderText("");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nombre = new TextField();
        nombre.setPromptText("Nombre");
        nombre.setText(viejaObra.getNombre());
        autor = new TextField();
        autor.setPromptText("Autor");
        autor.setText(viejaObra.getAutor());
        precio = new TextField();
        precio.setPromptText("Precio");
        precio.setText(String.valueOf(viejaObra.getPrecio()));
        tipo = new TextField();
        tipo.setPromptText("Tipo");
        tipo.setText(viejaObra.getTipo());
        tamanio = new TextField();
        tamanio.setPromptText("Tamaño");
        tamanio.setText(viejaObra.getTamanio());

        Label nombreActual = new Label("Nombre actual: ");
        nombreActual.setStyle("-fx-font-weight: bold;");

        Label autorActual = new Label("Autor actual: ");
        autorActual.setStyle("-fx-font-weight: bold;");

        Label precioActual = new Label("Precio actual: ");
        precioActual.setStyle("-fx-font-weight: bold;");

        Label tipoActual = new Label("Tipo actual: ");
        tipoActual.setStyle("-fx-font-weight: bold;");

        Label tamanioActual = new Label("Tamaño actual: ");
        tamanioActual.setStyle("-fx-font-weight: bold;");

        Label nombreNuevo = new Label("Nombre nuevo: ");
        nombreNuevo.setStyle("-fx-font-weight: bold;");

        Label autorNuevo = new Label("Autor nuevo: ");
        autorNuevo.setStyle("-fx-font-weight: bold;");

        Label precioNuevo = new Label("Precio nuevo: ");
        precioNuevo.setStyle("-fx-font-weight: bold;");

        Label tipoNuevo = new Label("Tipo nuevo: ");
        tipoNuevo.setStyle("-fx-font-weight: bold;");

        Label tamanioNuevo = new Label("Tamaño nuevo: ");
        tamanioNuevo.setStyle("-fx-font-weight: bold;");

        grid.add(nombreActual, 0, 0);
        grid.add(autorActual, 0, 1);
        grid.add(precioActual, 0, 2);
        grid.add(tipoActual, 0, 3);
        grid.add(tamanioActual, 0, 4);
        grid.add(new Label(viejaObra.getNombre()), 1, 0);
        grid.add(new Label(viejaObra.getAutor()), 1, 1);
        grid.add(new Label(String.valueOf(viejaObra.getPrecio())), 1, 2);
        grid.add(new Label(viejaObra.getTipo()), 1, 3);
        grid.add(new Label(viejaObra.getTamanio()), 1, 4);

        grid.add(nombreNuevo, 8, 0);
        grid.add(autorNuevo, 8, 1);
        grid.add(precioNuevo, 8, 2);
        grid.add(tipoNuevo, 8, 3);
        grid.add(tamanioNuevo, 8, 4);
        grid.add(nombre, 9, 0);
        grid.add(autor, 9, 1);
        grid.add(precio, 9, 2);
        grid.add(tipo, 9, 3);
        grid.add(tamanio, 9, 4);

        dialog.getDialogPane().setContent(grid);
    }

    public void show() {
        Platform.runLater(() -> {
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        });

        this.dialog.showAndWait();
    }

    public Obra getResult() {

        if(dialog.getResult() == ButtonType.OK) {
            Obra obra = new Obra();
            obra.setNombre(this.nombre.getText());
            obra.setAutor(this.autor.getText());
            obra.setTipo(this.tipo.getText());
            obra.setTamanio(this.tamanio.getText());
            obra.setHasImage(viejaObra.getHasImage());
            obra.setId(viejaObra.getId());

            if(this.precio.getText() == null || !Main.isNumeroValido(this.precio.getText()))
                obra.setPrecio(0);
            else
                obra.setPrecio(Main.safeDecode(this.precio.getText()));

            return obra;
        }
        else
            return null;
    }
}
