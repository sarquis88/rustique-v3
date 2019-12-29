package rustique.controllers;

import rustique.Main;
import rustique.misc.View;
import rustique.panes.*;

public class MainController implements Controller {

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
     * Respuesta a eventos
     * @param event tipo de evento
     */
    @Override
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
            case "trabajos":
                View.getInstance().changePane(TrabajosPane.getInstance());
                break;
            case "display":
                View.getInstance().changePane(DisplayPane.getInstance());
                break;
            case "salir":
                Main.getWindow().close();
                break;
            default:
                break;
        }
    }
}
