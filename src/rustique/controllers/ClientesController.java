package rustique.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rustique.Main;
import rustique.MessagesManager;
import rustique.bdd.RustiqueBDD;
import rustique.dialogs.CambiarClienteDialog;
import rustique.dialogs.ClienteDataDialog;
import rustique.dialogs.NuevoClienteDialog;
import rustique.dialogs.ShowClienteDialog;
import rustique.models.Cliente;
import rustique.panes.ClientesPane;

public class ClientesController {

    private static ClientesController thisController = null;
    private static ClientesPane thisPane = null;
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
            case "nuevo-cliente":
                nuevoCliente();
                break;
            case "borrar-cliente":
                borrarCliente(false);
                break;
            case "show-cliente-clickeado":
                showClienteClickeado();
                break;
            case "borrar-cliente-clickeado":
                borrarCliente(true);
                break;
            case "cambiar-cliente":
                cambiarCliente();
                break;
            default:
                break;
        }
    }

    /**
     * Agregado de cliente mediante input
     */
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

    /**
     * Input de cliente
     * @return cliente ingresado
     */
    private Cliente inputCliente() {
        NuevoClienteDialog nuevoClienteDialog = new NuevoClienteDialog("Nuevo cliente");
        nuevoClienteDialog.show();
        return nuevoClienteDialog.getResult();
    }

    /**
     * Input de Nombre o ID de cliente
     * @return dato ingresado
     */
    private String inputClienteData() {
        ClienteDataDialog clienteDataDialog = new ClienteDataDialog("Borrar cliente");
        clienteDataDialog.show();
        return clienteDataDialog.getResult();
    }
    /**
     * Borrado de cliente
     * @param clickeado true si se quiere borrar un cliente clickeado, de lo contrario false
     */
    private void borrarCliente(boolean clickeado) {
        Cliente cliente = null;

        if(!clickeado) {
            String input = inputClienteData();

            if (input != null && !input.isBlank()) {

                if (input.split("-")[0].equalsIgnoreCase("n")) {
                    cliente = getClienteByNombre(input.split("-")[1]);
                    if (cliente == null) {
                        MessagesManager.showErrorAlert("Nombre no existente");
                        return;
                    }
                } else {
                    if (Main.isNumeroValido(input.split("-")[1])) {
                        int id = Main.safeDecode(input.split("-")[1]);
                        cliente = getClienteById(id);
                        if (cliente == null) {
                            MessagesManager.showErrorAlert("ID invalido");
                            return;
                        }
                    } else {
                        MessagesManager.showErrorAlert("Numero invalido");
                        return;
                    }
                }
            }
        }
        else
            cliente = getClienteByNombre(ClientesPane.getInstance().getClienteClickeado());

        if(cliente == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        if(MessagesManager.confirmation("Borrar cliente " + cliente.getNombre().toUpperCase()
        + " ?")) {
            data.remove(cliente);
            RustiqueBDD.getInstance().deleteCliente(cliente.getId());
        }
    }

    /**
     * Getter de clientes
     * @return lista de clientes
     */
    public ObservableList<Cliente> getData() {
        return data;
    }

    /**
     * Agregar cliente a la lista
     * @param nuevoCliente nuevo cliente
     */
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

    /**
     * Devuelve el objeto Cliente que buscamos por nombre
     * @param nombre nombre del cliente buscado
     * @return cliente buscado
     */
    private Cliente getClienteByNombre(String nombre) {
        for(Cliente cliente : data)
            if(cliente.getNombre().equalsIgnoreCase(nombre))
                return cliente;
        return null;
    }

    /**
     * Devuelve el objeto Cliente que buscamos por id
     * @param id id del cliente buscado
     * @return cliente buscado
     */
    private Cliente getClienteById(int id) {
        for(Cliente cliente : data)
            if(cliente.getId() == id)
                return cliente;
        return null;
    }

    /**
     * Muestra de parametros de cliente que ha sido doblemente clickeado
     */
    private void showClienteClickeado() {
        Cliente clienteClickeado = getClienteByNombre(ClientesPane.getInstance().getClienteClickeado());

        if(clienteClickeado == null)
            MessagesManager.showFatalErrorAlert();
        else {
            ShowClienteDialog showClienteDialog = new ShowClienteDialog(clienteClickeado);
            showClienteDialog.show();
            ClientesPane.getInstance().resetClienteClickeado();
        }
    }

    /**
     * Permite realizar cambios en los parametros de un cliente
     */
    private void cambiarCliente() {
        Cliente clienteViejo = getClienteByNombre(ClientesPane.getInstance().getClienteClickeado());

        if (clienteViejo == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        CambiarClienteDialog cambiarClienteDialog = new CambiarClienteDialog(clienteViejo);
        cambiarClienteDialog.show();
        Cliente newCliente = cambiarClienteDialog.getResult();

        if(newCliente == null)		// cancel
            return;

        if(nombreExists(newCliente.getNombre()) &&
                !newCliente.getNombre().equalsIgnoreCase(clienteViejo.getNombre())) {
            MessagesManager.showErrorAlert("NOMBRE EXISTENTE");
            return;
        }
        if(!Main.isNombreValido(newCliente.getNombre())) {
            MessagesManager.showErrorAlert("NOMBRE INVALIDO");
            return;
        }

        RustiqueBDD.getInstance().cambiarCliente(clienteViejo.getNombre(),
                newCliente.getNombre(), newCliente.getSaldo(), newCliente.getComentarios());
        refreshData();
    }
}
