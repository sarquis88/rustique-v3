package rustique.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rustique.Main;
import rustique.MessagesManager;
import rustique.bdd.RustiqueBDD;
import rustique.dialogs.CambiarClienteDialog;
import rustique.dialogs.ObraDataDialog;
import rustique.dialogs.NuevoClienteDialog;
import rustique.dialogs.ShowClienteDialog;
import rustique.models.Cliente;
import rustique.panes.ClientesPane;

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
            case "nuevo-cliente":
                nuevoCliente();
                break;
            case "borrar-cliente":
                borrarCliente();
                break;
            case "show-cliente":
                showCliente();
                break;
            case "modificar-cliente":
                cambiarCliente();
                break;
            case "deseleccionar-cliente":
                deseleccionarCliente();
                break;
            default:
                break;
        }
    }

    /**
     * Agregado de cliente mediante input
     */
    private void nuevoCliente() {
        Cliente nuevoCliente = inputCliente("Nuevo cliente");

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
    private Cliente inputCliente(String titulo) {
        NuevoClienteDialog nuevoClienteDialog = new NuevoClienteDialog(titulo);
        nuevoClienteDialog.show();
        return nuevoClienteDialog.getResult();
    }

    /**
     * Input de Nombre o ID de cliente
     * @return dato ingresado
     */
    private String inputClienteData(String titulo) {
        ObraDataDialog obraDataDialog = new ObraDataDialog(titulo);
        obraDataDialog.show();
        return obraDataDialog.getResult();
    }
    /**
     * Borrado de cliente
     */
    private void borrarCliente() {
        Cliente cliente;
        String nombre = ClientesPane.getInstance().getClienteClickeado();

        if(nombre == null) {
                if( (cliente = buscarCliente("Buscar cliente a borrar")) == null)
                    return;
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
            ClientesPane.getInstance().resetClienteClickeado();
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
     * Muestra de parametros de cliente
     */
    private void showCliente() {

        String nombre = ClientesPane.getInstance().getClienteClickeado();
        Cliente cliente;

        if(nombre == null) {
            if ((cliente = buscarCliente("Buscar cliente")) == null)
                return;
        }
        else {
            cliente = getClienteByNombre(nombre);
        }

        if(cliente == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        ShowClienteDialog showClienteDialog = new ShowClienteDialog(cliente);
        showClienteDialog.show();
    }

    /**
     * Permite realizar cambios en los parametros de un cliente
     */
    private void cambiarCliente() {
        String nombre = ClientesPane.getInstance().getClienteClickeado();
        Cliente clienteViejo;

        if(nombre == null) {
            if( (clienteViejo = buscarCliente("Buscar cliente a modificar")) == null)
                return;
        }
        else
            clienteViejo = getClienteByNombre(nombre);

        if (clienteViejo == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        CambiarClienteDialog cambiarClienteDialog = new CambiarClienteDialog(clienteViejo);
        cambiarClienteDialog.show();
        Cliente newCliente = cambiarClienteDialog.getResult();

        if(newCliente == null)
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
        ClientesPane.getInstance().resetClienteClickeado();
    }

    /**
     * Permite buscar una obra por nombre o por id
     * @return objeto Obra buscada
     */
    private Cliente buscarCliente(String titulo) {
        String input = inputClienteData(titulo);
        Cliente clienteBuscado;

        if (input != null && !input.isEmpty()) {
            if (input.split("-")[0].equalsIgnoreCase("n")) {
                String nombre = input.split("-")[1];
                if (!nombreExists(nombre)) {
                    MessagesManager.showErrorAlert("Nombre no existente");
                    return null;
                }
                clienteBuscado = getClienteByNombre(nombre);
            } else {
                if (Main.isNumeroValido(input.split("-")[1])) {
                    int id = Main.safeDecode(input.split("-")[1]);
                    if (!idExists(id)) {
                        MessagesManager.showErrorAlert("ID invalido");
                        return null;
                    }
                    clienteBuscado = getClienteById(id);
                } else {
                    MessagesManager.showErrorAlert("Numero invalido");
                    return null;
                }
            }
        }
        else
            return null;

        if(clienteBuscado == null) {
            MessagesManager.showFatalErrorAlert();
            return null;
        }

        ClientesPane.getInstance().setClienteClickeado(clienteBuscado.getNombre());
        return clienteBuscado;
    }

    /**
     * Indica si existe algun cliente con el id ingresado
     * @param id id a saber si existe
     * @return true si existe el cliente, caso contrario false
     */
    private boolean idExists(int id) {
        for(Cliente cliente : data)
            if(cliente.getId() == id)
                return true;
        return false;
    }

    private void deseleccionarCliente() {
        ClientesPane.getInstance().resetClienteClickeado();
    }
}
