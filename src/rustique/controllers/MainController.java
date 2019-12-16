package rustique.controllers;

import javafx.stage.Stage;
import rustique.Main;
import rustique.View;
import rustique.panes.ClientesPane;
import rustique.panes.MainPane;
import rustique.panes.ObrasPane;
import rustique.panes.OpcionesPane;

public class MainController {

    private static MainController thisMainController = null;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static MainController getInstance() {
        if(thisMainController == null)
            thisMainController = new MainController();
        return thisMainController;
    }

    /**
     * Constructor de clase
     */
    private MainController() {
        thisMainController = this;
    }

    /**
     * Retorna ventana principal
     * @return objeto Stage
     */
    public Stage getWindow() {
        return Main.getWindow();
    }

    /**
     * Respuesta a eventos
     * @param event tipo de evento
     */
    public void actionPerformed(String event) {
        switch (event) {
            case "principal":
                View.getInstance().changePane(MainPane.getInstance());
                break;
            case "clientes":
                View.getInstance().changePane(ClientesPane.getInstance());
                break;
            case "obras":
                View.getInstance().changePane(ObrasPane.getInstance());
                break;
            case "opciones":
                View.getInstance().changePane(OpcionesPane.getInstance());
                break;
            case "salir":
                getWindow().close();
                break;
            default:
                break;
        }
    }
}
