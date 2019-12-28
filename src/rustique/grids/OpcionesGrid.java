package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.misc.RustiqueParameters;
import rustique.controllers.OpcionesController;

public class OpcionesGrid extends RustiqueGrid implements RustiqueParameters {

    private static OpcionesController thisController = null;
    private static OpcionesGrid thisOpcionesGrid = null;

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

        thisGrid = new GridPane();
        thisGrid.setHgap(10);
        thisGrid.setVgap(10);
        thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button restablecerBDD = new Button("Restablecer BDD");
        restablecerBDD.setPrefSize(buttonsWidth, buttonsHeight);
        restablecerBDD.setStyle(buttonsStyle);
        restablecerBDD.setOnAction(e -> thisController.actionPerformed("restablecer-bdd"));

        Button copiaBDD = new Button("Copia de BDD");
        copiaBDD.setPrefSize(buttonsWidth, buttonsHeight);
        copiaBDD.setStyle(buttonsStyle);
        copiaBDD.setOnAction(e -> thisController.actionPerformed("copia-bdd"));

        Button cambiarColor = new Button("Cambiar color");
        cambiarColor.setPrefSize(buttonsWidth, buttonsHeight);
        cambiarColor.setStyle(buttonsStyle);
        cambiarColor.setOnAction(e -> thisController.actionPerformed("cambiar-color"));

        Button cambiarDirectorio = new Button("Cambiar directorio");
        cambiarDirectorio.setPrefSize(buttonsWidth, buttonsHeight);
        cambiarDirectorio.setStyle(buttonsStyle);
        cambiarDirectorio.setOnAction(e -> thisController.actionPerformed("cambiar-directorio"));

        thisGrid.add(restablecerBDD, 0, 0);
        thisGrid.add(copiaBDD, 0, 1);
        thisGrid.add(cambiarColor, 0, 6);
        thisGrid.add(cambiarDirectorio, 0, 7);
    }
}
