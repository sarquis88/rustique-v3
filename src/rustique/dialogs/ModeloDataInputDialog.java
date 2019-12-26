package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rustique.misc.RustiqueParameters;

public class ModeloDataInputDialog extends RustiqueDialog implements RustiqueParameters {

    private TextField nombre;
    private TextField id;
    private ButtonType cancelar;
    private ButtonType nombreButton;
    private ButtonType idButton;

    /**
     * Constructor de la clase
     * @param title titulo de la ventana
     */
    public ModeloDataInputDialog(String title) {
        thisDialog = new Dialog<>();
        thisDialog.setTitle(title);
        thisDialog.setHeaderText("");

        nombreButton = new ButtonType("Por nombre");
        idButton = new ButtonType("Por id");
        cancelar = new ButtonType("Cancelar");

        thisDialog.getDialogPane().getButtonTypes().addAll(nombreButton);
        thisDialog.getDialogPane().getButtonTypes().addAll(idButton);
        thisDialog.getDialogPane().getButtonTypes().addAll(cancelar);

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        nombre = new TextField();
        nombre.setPromptText("Nombre");
        id = new TextField();
        id.setPromptText("ID");

        grid.add(new Label("Por nombre:"), 0, 0);
        grid.add(nombre, 1, 0);

        grid.add(new Label("ó"), 1, 1);

        grid.add(new Label("por ID:"), 0, 2);
        grid.add(id, 1, 2);

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Retorna datos ingresados de obra
     * @return objeto String con tipo de dato + dato
     */
    public String getResult() {
        if(thisDialog.getResult() == nombreButton) {
            if(!nombre.getText().isBlank())
                return "n-" + nombre.getText();
            else
                return null;
        }
        else if(thisDialog.getResult() == idButton) {
            if (!id.getText().isBlank())
                return "i-" + id.getText();
            else
                return null;
        }
        else if(thisDialog.getResult() == cancelar)
            return null;
        else
            return null;
    }
}
