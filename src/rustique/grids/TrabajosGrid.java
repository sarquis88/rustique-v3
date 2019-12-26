package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.misc.RustiqueParameters;
import rustique.controllers.TrabajosController;

public class TrabajosGrid extends RustiqueGrid implements RustiqueParameters {

    private static TrabajosController thisController = null;
    private static TrabajosGrid thisTrabajosGrid = null;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static TrabajosGrid getInstance() {
        if(thisTrabajosGrid == null)
            thisTrabajosGrid = new TrabajosGrid();
        return thisTrabajosGrid;
    }

    /**
     * Constructor de clase
     */
    private TrabajosGrid() {
        thisController = TrabajosController.getInstance();

        thisGrid = new GridPane();
        thisGrid.setHgap(10);
        thisGrid.setVgap(10);
        thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button nuevoTrabajo = new Button("Nuevo trabajo");
        nuevoTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        nuevoTrabajo.setStyle(buttonsStyle);
        nuevoTrabajo.setOnAction(e -> thisController.actionPerformed("nuevo-trabajo"));

        Button borrarTrabajo = new Button("Borrar trabajo");
        borrarTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        borrarTrabajo.setStyle(buttonsStyle);
        borrarTrabajo.setOnAction(e -> thisController.actionPerformed("borrar-trabajo"));

        thisGrid.add(nuevoTrabajo, 0, 0);
        thisGrid.add(borrarTrabajo, 0, 1);
    }
}
