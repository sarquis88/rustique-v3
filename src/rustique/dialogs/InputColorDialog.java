package rustique.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import rustique.misc.RustiqueParameters;

public class InputColorDialog extends RustiqueDialog implements RustiqueParameters {

    private ColorPicker colorPicker;

    public InputColorDialog() {
        thisDialog = new Dialog<>();
        thisDialog.setTitle("Elegir color");
        thisDialog.setHeaderText("");

        ButtonType cancelar = new ButtonType("Cancelar");

        thisDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        thisDialog.getDialogPane().getButtonTypes().addAll(cancelar);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        colorPicker = new ColorPicker();

        grid.add(colorPicker, 0, 0);

        thisDialog.getDialogPane().setContent(grid);
    }

    /**
     * Retorna el color elegido
     * @return objeto Color
     */
    public Color getResult() {
        if(thisDialog.getResult() == ButtonType.OK)
            return colorPicker.getValue();
        else
            return null;
    }
}
