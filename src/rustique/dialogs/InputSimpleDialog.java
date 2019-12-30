package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import rustique.misc.RustiqueParameters;

public class InputSimpleDialog extends RustiqueDialog implements RustiqueParameters {

    private TextField textField;

    public InputSimpleDialog(String title) {
        thisDialog = new Dialog<>();
        thisDialog.setTitle(title);
        thisDialog.setHeaderText("");

        ButtonType cancelar = new ButtonType("Cancelar");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        thisDialog.getDialogPane().getButtonTypes().addAll(cancelar);

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        textField = new TextField();
        textField.setPromptText(title);
        textField.setPrefWidth(buttonsWidth * 2);

        grid.add(textField, 0, 0);

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Retorna el texto ingresado
     * @return objeto String con texto
     */
    public String getResult() {
        if(thisDialog.getResult() == ButtonType.OK)
            return textField.getText();
        else
            return null;
    }
}
