package rustique.controllers;

import rustique.dialogs.InputSimpleDialog;
import rustique.panes.DisplayPane;

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
            case "ordenar-autor":
                ordenarPorAutor();
                break;
            case "ordenar-nombre":
                ordenarPorNombre();
                break;
            case "ordenar-tipo":
                ordenarPorTipo();
                break;
            default:
                break;
        }
    }

    /**
     * Permite ordenar el display mediante el input de un nombre
     */
    private void ordenarPorNombre() {
        InputSimpleDialog inputSimpleDialog = new InputSimpleDialog("Ingresar nombre");
        inputSimpleDialog.show();

        String nombre = inputSimpleDialog.getResult();
        if(nombre != null)
            DisplayPane.getInstance().mostrarPorAtributo(0, nombre);
    }

    /**
     * Permite ordenar el display mediante el input de un autor
     */
    private void ordenarPorAutor() {
        InputSimpleDialog inputSimpleDialog = new InputSimpleDialog("Ingresar autor");
        inputSimpleDialog.show();

        String autor = inputSimpleDialog.getResult();
        if(autor != null)
            DisplayPane.getInstance().mostrarPorAtributo(1, autor);
    }

    /**
     * Permite ordenar el display mediante el input de un tipo
     */
    private void ordenarPorTipo() {
        InputSimpleDialog inputSimpleDialog = new InputSimpleDialog("Ingresar tipo");
        inputSimpleDialog.show();

        String tipo = inputSimpleDialog.getResult();
        if(tipo != null)
            DisplayPane.getInstance().mostrarPorAtributo(2, tipo);
    }
}
