package rustique.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rustique.Main;
import rustique.MessagesManager;
import rustique.bdd.RustiqueBDD;
import rustique.dialogs.NuevoClienteDialog;
import rustique.models.Cliente;

public class ClientesController {

    private static ClientesController thisController = null;

    private static ObservableList<Cliente> data = FXCollections.observableArrayList();

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static ClientesController getInstance() {
        if(thisController == null)
            thisController = new ClientesController();
        return thisController;
    }

    /**
     * Constructor de clase
     */
    private ClientesController() {
        thisController = this;
    }

    /**
     * Respuesta a eventos
     * @param event tipo de evento
     */
    public void actionPerformed(String event) {
        switch (event) {
            case "nuevo cliente":
                nuevoCliente();
                break;
            case "borrar cliente":
                borrarCliente();
                break;
            default:
                break;
        }
    }

    private void nuevoCliente() {
        Cliente nuevoCliente = inputCliente();

        if(nuevoCliente != null) {
            if(Main.isNombreValido(nuevoCliente.getNombre())) {

                if(nombreExists(nuevoCliente.getNombre()))
                    MessagesManager.showErrorAlert("Nombre existente");
                else {
                    RustiqueBDD.getInstance().insertarCliente(Cliente.getGlobalId(),
                            nuevoCliente.getNombre(), nuevoCliente.getSaldo(), nuevoCliente.getComentarios());

                    refreshData();
                }
            }
            else
                MessagesManager.showErrorAlert("Nombre invalido");
        }
    }

    private Cliente inputCliente() {
        NuevoClienteDialog nuevoClienteDialog = new NuevoClienteDialog("Nuevo Cliente");
        nuevoClienteDialog.show();
        return nuevoClienteDialog.getResult();
    }

    private void borrarCliente() {
        System.out.println("borrar cliente");
    }

    public ObservableList<Cliente> getData() {
        return data;
    }

    public static void addCliente(Cliente nuevoCliente) {
        data.add(nuevoCliente);
    }

    /**
     * Indica si existe algun cliente con el nombre ingresado
     * @param nombre nombre a saber si existe
     * @return true si existe el cliente, caso contrario false
     */
    private boolean nombreExists(String nombre) {
        for(Cliente cliente : data)
            if(cliente.getNombre().equalsIgnoreCase(nombre))
                return true;
        return false;
    }

    /**
     * Elimina todos los clientes y los vuelve a cargar
     */
    private void refreshData() {
        data.clear();
        RustiqueBDD.getInstance().restoreClientesFromBDD();
    }
}
