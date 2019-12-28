package rustique.controllers;

import rustique.dialogs.InputPathDialog;
import rustique.misc.ImagesManager;
import rustique.Main;
import rustique.misc.MessagesManager;
import rustique.bdd.RustiqueBDD;
import rustique.misc.View;

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
     * Respuesta a eventos
     * @param event tipo de evento
     */
    public void actionPerformed(String event) {
        switch (event) {
            case "restablecer-bdd":
                restablecerBDD();
                break;
            case "copia-bdd":
                ImagesManager.copiarBDD();
                break;
            case "cambiar-color":
                View.getInstance().cambiarColor();
                break;
            case "cambiar-directorio":
                cambiarDirectorio();
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

    private void cambiarDirectorio() {
        InputPathDialog inputPathDialog = new InputPathDialog();
        inputPathDialog.show();

        String path = inputPathDialog.getResult();
        if(path != null)
            RustiqueBDD.getInstance().insertarDirInicial(path);
    }
}
