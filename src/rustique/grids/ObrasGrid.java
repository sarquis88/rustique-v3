package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.RustiqueParameters;
import rustique.controllers.ObrasController;

public class ObrasGrid implements RustiqueParameters {

    private static ObrasController thisController = null;
    private static ObrasGrid thisObrasGrid = null;

    private GridPane grid;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static ObrasGrid getInstance() {
        if(thisObrasGrid == null)
            thisObrasGrid = new ObrasGrid();
        return thisObrasGrid;
    }

    /**
     * Constructor de clase
     */
    private ObrasGrid() {
        thisController = ObrasController.getInstance();

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button nuevaObra = new Button("Nueva obra");
        nuevaObra.setPrefSize(buttonsWidth, buttonsHeight);
        nuevaObra.setStyle(buttonsStyle);
        nuevaObra.setOnAction(e -> thisController.actionPerformed("nueva-obra"));

        Button borrarObra = new Button("Borrar obra");
        borrarObra.setPrefSize(buttonsWidth, buttonsHeight);
        borrarObra.setStyle(buttonsStyle);
        borrarObra.setOnAction(e -> thisController.actionPerformed("borrar-obra"));

        grid.add(nuevaObra, 0, 0);
        grid.add(borrarObra, 0, 1);
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
