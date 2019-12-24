package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import rustique.controllers.MainController;
import rustique.ImagesManager;
import rustique.RustiqueParameters;

public class MainGrid implements RustiqueParameters {

    private static MainController thisMainController;

    private Button principal;
    private Button clientes;
    private Button obras;
    private Button trabajos;
    private Button opciones;
    private Button salir;
    private GridPane grid;

    /**
     * Constructor de clase
     */
    public MainGrid() {
        thisMainController = MainController.getInstance();
        String logoPath = "./src/images/logo.png";

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        ImageView logo = ImagesManager.getImageView(logoPath);
        if(logo != null) {
            logo.setLayoutX(0);
            logo.setLayoutY(0);
        }

        this.principal = new Button("Principal");
        this.principal.setPrefSize(buttonsWidth, buttonsHeight);
        this.principal.setStyle(buttonsStyle);
        this.principal.setOnAction(e -> {
            enableAll("principal");
            thisMainController.actionPerformed("principal");
        });

        this.clientes = new Button("Clientes");
        this.clientes.setPrefSize(buttonsWidth, buttonsHeight);
        this.clientes.setStyle(buttonsStyle);
        this.clientes.setOnAction(e -> {
            enableAll("clientes");
            thisMainController.actionPerformed("clientes");
        });

        this.obras = new Button("Obras");
        this.obras.setPrefSize(buttonsWidth, buttonsHeight);
        this.obras.setStyle(buttonsStyle);
        this.obras.setOnAction(e -> {
            enableAll("obras");
            thisMainController.actionPerformed("obras");
        });

        this.trabajos = new Button("Trabajos");
        this.trabajos.setPrefSize(buttonsWidth, buttonsHeight);
        this.trabajos.setStyle(buttonsStyle);
        this.trabajos.setOnAction(e -> {
            enableAll("trabajos");
            thisMainController.actionPerformed("trabajos");
        });

        this.opciones = new Button("Opciones");
        this.opciones.setPrefSize(buttonsWidth, buttonsHeight);
        this.opciones.setStyle(buttonsStyle);
        this.opciones.setOnAction(e -> {
            enableAll("opciones");
            thisMainController.actionPerformed("opciones");
        });

        this.salir = new Button("Salir");
        this.salir.setPrefSize(buttonsWidth, buttonsHeight);
        this.salir.setStyle(buttonsStyle);
        this.salir.setOnAction(e -> {
            enableAll("salir");
            thisMainController.actionPerformed("salir");
        });

        grid.add(logo, 0, 0);
        grid.add(principal, 0, 1);
        grid.add(clientes, 0, 2);
        grid.add(obras, 0, 3);
        grid.add(trabajos, 0, 4);
        grid.add(opciones, 0, 9);
        grid.add(salir, 0, 30);

        if(logo != null)
            grid.setPrefWidth(logo.getFitWidth() + hPadding * 2);
    }

    /**
     * Retorna el grid para que las vistas lo utilicen
     * @return objeto nodo GridPane
     */
    public GridPane getGridPane() {
        return this.grid;
    }

    /**
     * Desactiva o activa el boton elegido
     * @param boton boton a activar/desactivar
     * @param bool true para desactivar, false para activar
     */
    public void setDisable(String boton, boolean bool) {
        switch(boton) {
            case "principal":
                this.principal.setDisable(bool);
                break;
            case "clientes":
                this.clientes.setDisable(bool);
                break;
            case "obras":
                this.obras.setDisable(bool);
                break;
            case "salir":
                this.salir.setDisable(bool);
                break;
            case "opciones":
                this.opciones.setDisable(bool);
                break;
            default:
                break;
        }
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

    /**
     * Activa todos los botones excepto el indicado en el parametro
     * @param exception boton a desactivar
     */
    private void enableAll(String exception) {

        this.principal.setDisable(false);
        this.clientes.setDisable(false);
        this.obras.setDisable(false);
        this.trabajos.setDisable(false);
        this.opciones.setDisable(false);
        this.salir.setDisable(false);

        switch (exception) {
            case "principal":
                this.principal.setDisable(true);
                break;
            case "clientes":
                this.clientes.setDisable(true);
                break;
            case "obras":
                this.obras.setDisable(true);
                break;
            case "trabajos":
                this.trabajos.setDisable(true);
                break;
            case "opciones":
                this.opciones.setDisable(true);
                break;
            case "salir":
                this.salir.setDisable(true);
                break;
        }
    }
}
