package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ClienteDataDialog {

    private Dialog<ButtonType> dialog;
    private TextField nombre;
    private TextField id;
    private ButtonType cancelar;
    private ButtonType nombreButton;
    private ButtonType idButton;

    /**
     * Constructor de la clase
     * @param title titulo de la ventana
     */
    public ClienteDataDialog(String title) {
        dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("");

        nombreButton = new ButtonType("Por nombre");
        idButton = new ButtonType("Por id");
        cancelar = new ButtonType("Cancelar");

        dialog.getDialogPane().getButtonTypes().addAll(nombreButton);
        dialog.getDialogPane().getButtonTypes().addAll(idButton);
        dialog.getDialogPane().getButtonTypes().addAll(cancelar);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nombre = new TextField();
        nombre.setPromptText("Nombre");
        id = new TextField();
        id.setPromptText("ID");

        grid.add(new Label("Por nombre:"), 0, 0);
        grid.add(nombre, 1, 0);

        grid.add(new Label("ó"), 1, 1);

        grid.add(new Label("por ID:"), 0, 2);
        grid.add(id, 1, 2);

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
     * Retorna datos ingresados de cliente
     * @return objeto String con tipo de dato + dato
     */
    public String getResult() {
        if(dialog.getResult() == nombreButton) {
            if(!nombre.getText().isBlank())
                return "n-" + nombre.getText();
            else
                return null;
        }
        else if(dialog.getResult() == idButton) {
            if (!id.getText().isBlank())
                return "i-" + id.getText();
            else
                return null;
        }
        else
            return null;
    }
}
