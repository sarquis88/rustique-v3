package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import rustique.Main;
import rustique.bdd.RustiqueBDD;
import rustique.misc.RustiqueParameters;

public class InputPathDialog extends RustiqueDialog implements RustiqueParameters {

    private TextField textField;

    /**
     * Constructor de la clase
     */
    public InputPathDialog() {
        thisDialog = new Dialog<>();
        thisDialog.setTitle("Ingresar dirección");
        thisDialog.setHeaderText("");

        ButtonType cancelar = new ButtonType("Cancelar");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        thisDialog.getDialogPane().getButtonTypes().addAll(cancelar);

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        textField = new TextField();
        textField.setText(RustiqueBDD.getInstance().getInitialDir());
        textField.setPromptText("Dirección -->");
        textField.setPrefWidth(buttonsWidth * 3);

        Button buscarDirectorio = new Button("Buscar");
        buscarDirectorio.setPrefSize(buttonsWidth / 2, buttonsHeight);
        buscarDirectorio.setStyle(buttonsStyle);
        buscarDirectorio.setOnAction(e -> buscarDirectorio());

        grid.add(textField, 0, 0);
        grid.add(buscarDirectorio, 1, 0);

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Retorna el directorio elegido
     * @return objeto String con path
     */
    public String getResult() {
        if(thisDialog.getResult() == ButtonType.OK)
            return textField.getText();
        else
            return null;
    }

    /**
     * Dialogo de input real
     */
    private void buscarDirectorio() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String path;
        try {
            path = directoryChooser.showDialog(Main.getWindow()).toURI().getPath();
        }
        catch (NullPointerException e) {
            return;
        }
        if(path == null)
            path = "";
        textField.setText(path);
    }
}
