package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import rustique.controllers.MainController;
import rustique.misc.ImagesManager;
import rustique.misc.RustiqueParameters;

public class MainGrid extends RustiqueGrid implements RustiqueParameters {

    private static MainController thisMainController;

    private Button principal;
    private Button clientes;
    private Button obras;
    private Button trabajos;
    private Button opciones;
    private Button salir;

    /**
     * Constructor de clase
     */
    public MainGrid() {
        thisMainController = MainController.getInstance();
        String logoPath = "./src/images/logo.png";

        thisGrid = new GridPane();
        thisGrid.setHgap(10);
        thisGrid.setVgap(10);
        thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

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

        thisGrid.add(logo, 0, 0);
        thisGrid.add(principal, 0, 1);
        thisGrid.add(clientes, 0, 6);
        thisGrid.add(obras, 0, 7);
        thisGrid.add(trabajos, 0, 8);
        thisGrid.add(opciones, 0, 13);
        thisGrid.add(salir, 0, 30);

        if(logo != null)
            thisGrid.setPrefWidth(logo.getFitWidth() + hPadding * 2);
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
