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

public class NuevaObraDialog {

    private Dialog<ButtonType> dialog;
    private TextField nombre;
    private TextField autor;
    private TextField tipo;
    private TextField tamanio;
    private TextField precio;

    /**
     * Constructor de clase
     * @param title titulo de dialogo
     */
    public NuevaObraDialog(String title) {

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
     * Retorna obra ingresada en dialogo
     * @return objeto Obra ingresado
     */
    public Obra getResult() {
        Obra obra = new Obra();

        if(dialog.getResult() == ButtonType.OK) {
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
        else
            return null;
    }
}
