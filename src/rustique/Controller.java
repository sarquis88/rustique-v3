package rustique;

import javafx.scene.Scene;
import javafx.stage.Stage;
import rustique.panes.ClientesPane;
import rustique.panes.MainPane;
import rustique.panes.ObrasPane;
import rustique.panes.OpcionesPane;

public class Controller {

    private static Controller thisController = null;
    private static View thisView = null;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static Controller getInstance() {
        if(thisController == null)
            thisController = new Controller();
        return thisController;
    }

    /**
     * Constructor de clase
     */
    private Controller() {
        thisController = this;
        thisView = View.getInstance(thisController);
    }

    /**
     * Devuelve ventana de vista
     * @return objeto Scene
     */
    public Scene getScene() {
        return thisView.getScene();
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
                thisView.changePane(MainPane.getInstance());
                break;
            case "clientes":
                thisView.changePane(ClientesPane.getInstance());
                break;
            case "obras":
                thisView.changePane(ObrasPane.getInstance());
                break;
            case "opciones":
                thisView.changePane(OpcionesPane.getInstance());
                break;
            case "salir":
                getWindow().close();
                break;
            default:
                break;
        }
    }
}
