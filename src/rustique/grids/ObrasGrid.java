package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.misc.MessagesManager;
import rustique.misc.RustiqueParameters;
import rustique.controllers.ObrasController;

public class ObrasGrid extends RustiqueGrid implements RustiqueParameters {

    private static ObrasController thisController = null;
    private static ObrasGrid thisObrasGrid = null;

    private Button nuevaObra;
    private Button borrarObra;
    private Button buscarObra;
    private Button modificarObra;
    private Button deseleccionarObra;

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

        thisGrid = new GridPane();
        thisGrid.setHgap(10);
        thisGrid.setVgap(10);
        thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        nuevaObra = new Button("Nueva obra");
        nuevaObra.setPrefSize(buttonsWidth, buttonsHeight);
        nuevaObra.setStyle(buttonsStyle);
        nuevaObra.setOnAction(e -> thisController.actionPerformed("nueva-obra"));

        borrarObra = new Button("Borrar obra");
        borrarObra.setPrefSize(buttonsWidth, buttonsHeight);
        borrarObra.setStyle(buttonsStyle);
        borrarObra.setOnAction(e -> thisController.actionPerformed("borrar"));

        modificarObra = new Button("Modificar obra");
        modificarObra.setPrefSize(buttonsWidth, buttonsHeight);
        modificarObra.setStyle(buttonsStyle);
        modificarObra.setOnAction(e -> thisController.actionPerformed("modificar"));

        buscarObra = new Button("Buscar obra");
        buscarObra.setPrefSize(buttonsWidth, buttonsHeight);
        buscarObra.setStyle(buttonsStyle);
        buscarObra.setOnAction(e -> thisController.actionPerformed("show-obra"));

        Button verFotos = new Button("Ver fotos");
        verFotos.setPrefSize(buttonsWidth, buttonsHeight);
        verFotos.setStyle(buttonsStyle);
        verFotos.setOnAction(e -> thisController.actionPerformed("ver-fotos"));

        deseleccionarObra = new Button("Deseleccionar obra");
        deseleccionarObra.setPrefSize(buttonsWidth, buttonsHeight);
        deseleccionarObra.setStyle(buttonsStyle);
        deseleccionarObra.setOnAction(e -> thisController.actionPerformed("deseleccionar-obra"));
        deseleccionarObra.setDisable(true);

        thisGrid.add(nuevaObra, 0, 0);
        thisGrid.add(borrarObra, 0, 5);
        thisGrid.add(modificarObra, 0, 6);
        thisGrid.add(buscarObra, 0, 7);
        thisGrid.add(verFotos, 0, 12);
        thisGrid.add(deseleccionarObra, 0, 17);
    }

    public void setDisable(String button, boolean disable) {
        Button boton = null;

        switch (button) {
            case "nuevaObra":
                boton = this.nuevaObra;
                break;
            case "borrarObra":
                boton = this.borrarObra;
                break;
            case "modificarObra":
                boton = this.modificarObra;
                break;
            case "buscarObra":
                boton = this.buscarObra;
                break;
            case "deseleccionarObra":
                boton = this.deseleccionarObra;
                break;
        }

        if(boton == null)
            MessagesManager.showFatalErrorAlert();
        else
            boton.setDisable(disable);
    }
}
