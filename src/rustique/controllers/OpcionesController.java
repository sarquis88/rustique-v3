package rustique.controllers;

import javafx.stage.Stage;
import rustique.misc.ImagesManager;
import rustique.Main;
import rustique.misc.MessagesManager;
import rustique.bdd.RustiqueBDD;

public class OpcionesController {

    private static OpcionesController thisOpcionesController = null;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static OpcionesController getInstance() {
        if(thisOpcionesController == null)
            thisOpcionesController = new OpcionesController();
        return thisOpcionesController;
    }

    /**
     * Constructor de clase
     */
    private OpcionesController() {
        thisOpcionesController = this;
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
            case "restablecer-bdd":
                restablecerBDD();
                break;
            case "copia-bdd":
                copiaBDD();
                break;
            default:
                break;
        }
    }

    private void restablecerBDD() {
        if(MessagesManager.confirmation("Desea borrar todos los datos?")) {
            if(MessagesManager.confirmation("Confirmar borrar todos los datos")) {
                RustiqueBDD.getInstance().restablecerBDD();
                ImagesManager.removeAllImages();
                MessagesManager.showInformationAlert("Reiniciar aplicacion");
                Main.getWindow().close();
            }
        }
    }

    private void copiaBDD() {
        ImagesManager.copiarBDD();
    }
}
