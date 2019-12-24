package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.RustiqueParameters;
import rustique.controllers.OpcionesController;

public class OpcionesGrid implements RustiqueParameters {

    private static OpcionesController thisController = null;
    private static OpcionesGrid thisOpcionesGrid = null;

    private GridPane grid;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static OpcionesGrid getInstance() {
        if(thisOpcionesGrid == null)
            thisOpcionesGrid = new OpcionesGrid();
        return thisOpcionesGrid;
    }

    /**
     * Constructor de clase
     */
    private OpcionesGrid() {
        thisController = OpcionesController.getInstance();

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button restablecerBDD = new Button("Restablecer BDD");
        restablecerBDD.setPrefSize(buttonsWidth, buttonsHeight);
        restablecerBDD.setStyle(buttonsStyle);
        restablecerBDD.setOnAction(e -> thisController.actionPerformed("restablecer-bdd"));

        grid.add(restablecerBDD, 0, 0);
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
