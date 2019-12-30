package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.controllers.DisplayController;
import rustique.misc.RustiqueParameters;

public class DisplayGrid extends RustiqueGrid implements RustiqueParameters {

    private static DisplayController thisDisplayController = null;
    private static DisplayGrid thisDisplayGrid = null;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static DisplayGrid getInstance() {
        if(thisDisplayGrid == null)
            thisDisplayGrid = new DisplayGrid();
        return thisDisplayGrid;
    }

    /**
     * Constructor de clase
     */
    private DisplayGrid() {
        thisDisplayController = DisplayController.getInstance();

        thisGrid = new GridPane();
        thisGrid.setHgap(10);
        thisGrid.setVgap(10);
        thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button porNombre = new Button("Por nombre");
        porNombre.setPrefSize(buttonsWidth, buttonsHeight);
        porNombre.setStyle(buttonsStyle);
        porNombre.setOnAction(e -> thisDisplayController.actionPerformed("ordenar-nombre"));

        Button porAutor = new Button("Por autor");
        porAutor.setPrefSize(buttonsWidth, buttonsHeight);
        porAutor.setStyle(buttonsStyle);
        porAutor.setOnAction(e -> thisDisplayController.actionPerformed("ordenar-autor"));

        Button porTipo = new Button("Por tipo");
        porTipo.setPrefSize(buttonsWidth, buttonsHeight);
        porTipo.setStyle(buttonsStyle);
        porTipo.setOnAction(e -> thisDisplayController.actionPerformed("ordenar-tipo"));

        thisGrid.add(porNombre, 0, 0);
        thisGrid.add(porAutor, 0, 1);
        thisGrid.add(porTipo, 0, 2);
    }
}
