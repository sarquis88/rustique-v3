package rustique.controllers;

public class DisplayController implements Controller {

    private static DisplayController thisController = null;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static DisplayController getInstance() {
        if(thisController == null)
            thisController = new DisplayController();
        return thisController;
    }

    /**
     * Constructor de clase
     */
    private DisplayController() {
        thisController = this;
    }

    /**
     * Respuesta a eventos
     * @param event tipo de evento
     */
    @Override
    public void actionPerformed(String event) {
        switch (event) {
            case "display":
                display();
                break;
            default:
                break;
        }
    }

    private void display() {

    }
}
