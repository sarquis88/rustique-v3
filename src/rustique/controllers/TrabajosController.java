package rustique.controllers;

public class TrabajosController {

    private static TrabajosController thisTrabajosController = null;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static TrabajosController getInstance() {
        if(thisTrabajosController == null)
            thisTrabajosController = new TrabajosController();
        return thisTrabajosController;
    }

    /**
     * Constructor de clase
     */
    private TrabajosController() {
        thisTrabajosController = this;
    }

    /**
     * Respuesta a eventos
     * @param event tipo de evento
     */
    public void actionPerformed(String event) {
        switch (event) {
            default:
                break;
        }
    }
}
