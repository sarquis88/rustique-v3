package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.misc.MessagesManager;
import rustique.misc.RustiqueParameters;
import rustique.controllers.TrabajosController;

public class TrabajosGrid extends RustiqueGrid implements RustiqueParameters {

    private static TrabajosController thisController = null;
    private static TrabajosGrid thisTrabajosGrid = null;

    private Button nuevoTrabajo;
    private Button borrarTrabajo;
    private Button modificarTrabajo;
    private Button buscarTrabajo;
    private Button deseleccionarTrabajo;

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

        nuevoTrabajo = new Button("Nuevo trabajo");
        nuevoTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        nuevoTrabajo.setStyle(buttonsStyle);
        nuevoTrabajo.setOnAction(e -> thisController.actionPerformed("nuevo"));

        borrarTrabajo = new Button("Borrar trabajo");
        borrarTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        borrarTrabajo.setStyle(buttonsStyle);
        borrarTrabajo.setOnAction(e -> thisController.actionPerformed("borrar"));

        modificarTrabajo = new Button("Modificar trabajo");
        modificarTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        modificarTrabajo.setStyle(buttonsStyle);
        modificarTrabajo.setOnAction(e -> thisController.actionPerformed("modificar"));

        buscarTrabajo = new Button("Buscar trabajo");
        buscarTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        buscarTrabajo.setStyle(buttonsStyle);
        buscarTrabajo.setOnAction(e -> thisController.actionPerformed("show-trabajo"));

        deseleccionarTrabajo = new Button("Deseleccionar trabajo");
        deseleccionarTrabajo.setPrefSize(buttonsWidth, buttonsHeight);
        deseleccionarTrabajo.setStyle(buttonsStyle);
        deseleccionarTrabajo.setOnAction(e -> thisController.actionPerformed("deseleccionar-trabajo"));
        deseleccionarTrabajo.setDisable(true);

        thisGrid.add(nuevoTrabajo, 0, 0);
        thisGrid.add(borrarTrabajo, 0, 5);
        thisGrid.add(modificarTrabajo, 0, 6);
        thisGrid.add(buscarTrabajo, 0, 7);
        thisGrid.add(deseleccionarTrabajo, 0, 12);
    }

    public void setDisable(String button, boolean disable) {
        Button boton = null;

        switch (button) {
            case "nuevoTrabajo":
                boton = this.nuevoTrabajo;
                break;
            case "borrarTrabajo":
                boton = this.borrarTrabajo;
                break;
            case "modificarTrabajo":
                boton = this.modificarTrabajo;
                break;
            case "buscarTrabajo":
                boton = this.buscarTrabajo;
                break;
            case "deseleccionarTrabajo":
                boton = this.deseleccionarTrabajo;
                break;
        }

        if(boton == null)
            MessagesManager.showFatalErrorAlert();
        else
            boton.setDisable(disable);
    }
}
