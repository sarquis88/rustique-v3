package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.MessagesManager;
import rustique.controllers.ClientesController;
import rustique.RustiqueParameters;

public class ClientesGrid implements RustiqueParameters {

    private static ClientesController thisController = null;
    private static ClientesGrid thisClientesGrid = null;

    private Button nuevoCliente;
    private Button borrarCliente;
    private Button modificarCliente;
    private Button buscarCliente;
    private Button deseleccionarCliente;

    private GridPane grid;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static ClientesGrid getInstance() {
        if(thisClientesGrid == null)
            thisClientesGrid = new ClientesGrid();
        return thisClientesGrid;
    }

    /**
     * Constructor de clase
     */
    private ClientesGrid() {
        thisController = ClientesController.getInstance();

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        nuevoCliente = new Button("Nuevo cliente");
        nuevoCliente.setPrefSize(buttonsWidth, buttonsHeight);
        nuevoCliente.setStyle(buttonsStyle);
        nuevoCliente.setOnAction(e -> thisController.actionPerformed("nuevo-cliente"));

        borrarCliente = new Button("Borrar cliente");
        borrarCliente.setPrefSize(buttonsWidth, buttonsHeight);
        borrarCliente.setStyle(buttonsStyle);
        borrarCliente.setOnAction(e -> thisController.actionPerformed("borrar"));

        modificarCliente = new Button("Modificar cliente");
        modificarCliente.setPrefSize(buttonsWidth, buttonsHeight);
        modificarCliente.setStyle(buttonsStyle);
        modificarCliente.setOnAction(e -> thisController.actionPerformed("modificar"));

        buscarCliente = new Button("Buscar cliente");
        buscarCliente.setPrefSize(buttonsWidth, buttonsHeight);
        buscarCliente.setStyle(buttonsStyle);
        buscarCliente.setOnAction(e -> thisController.actionPerformed("buscar-cliente"));

        deseleccionarCliente = new Button("Deseleccionar cliente");
        deseleccionarCliente.setPrefSize(buttonsWidth, buttonsHeight);
        deseleccionarCliente.setStyle(buttonsStyle);
        deseleccionarCliente.setOnAction(e -> thisController.actionPerformed("deseleccionar-cliente"));
        deseleccionarCliente.setDisable(true);

        grid.add(nuevoCliente, 0, 0);
        grid.add(borrarCliente, 0, 5);
        grid.add(modificarCliente, 0, 6);
        grid.add(buscarCliente, 0, 7);
        grid.add(deseleccionarCliente, 0, 12);
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

    public void setDisable(String button, boolean disable) {
        Button boton = null;

        switch (button) {
            case "nuevoCliente":
                boton = this.nuevoCliente;
                break;
            case "borrarCliente":
                boton = this.borrarCliente;
                break;
            case "modificarCliente":
                boton = this.modificarCliente;
                break;
            case "buscarCliente":
                boton = this.buscarCliente;
                break;
            case "deseleccionarCliente":
                boton = this.deseleccionarCliente;
                break;
        }

        if(boton == null)
            MessagesManager.showFatalErrorAlert();
        else
            boton.setDisable(disable);
    }
}
