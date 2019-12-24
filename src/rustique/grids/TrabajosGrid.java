package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.RustiqueParameters;
import rustique.controllers.TrabajosController;

public class TrabajosGrid implements RustiqueParameters {

    private static TrabajosController thisController = null;
    private static TrabajosGrid thisTrabajosGrid = null;

    private GridPane grid;

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

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button nuevoTrabajo = new Button("Nuevo trabajo");
        nuevoTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        nuevoTrabajo.setStyle(buttonsStyle);
        nuevoTrabajo.setOnAction(e -> thisController.actionPerformed("nuevo-trabajo"));

        Button borrarTrabajo = new Button("Borrar trabajo");
        borrarTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        borrarTrabajo.setStyle(buttonsStyle);
        borrarTrabajo.setOnAction(e -> thisController.actionPerformed("borrar-trabajo"));

        grid.add(nuevoTrabajo, 0, 0);
        grid.add(borrarTrabajo, 0, 1);
    }

    /**
     * Retorna el grid para que las vistas lo utilicen
     * @return objeto nodo GridPane
     */
    public GridPane getGridPane() {
        return this.grid;
    }

    /**
     * Seteo de posicion de la grid
     * @param x layout x
     * @param y layout y
     */
    public void setLayout(double x, double y) {
        this.grid.setLayoutX(x);
        this.grid.setLayoutY(y);
    }
}
