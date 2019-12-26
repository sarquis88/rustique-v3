package rustique.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rustique.Main;
import rustique.bdd.RustiqueBDD;
import rustique.dialogs.CambiarModeloDialog;
import rustique.dialogs.ModeloDataInputDialog;
import rustique.dialogs.NuevoModeloDialog;
import rustique.dialogs.ShowModeloDialog;
import rustique.misc.MessagesManager;
import rustique.models.Trabajo;
import rustique.panes.TrabajosPane;

public class TrabajosController implements Controller {

    private static TrabajosController thisController = null;

    private static ObservableList<Trabajo> data = FXCollections.observableArrayList();

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static TrabajosController getInstance() {
        if(thisController == null)
            thisController = new TrabajosController();
        return thisController;
    }

    /**
     * Constructor de clase
     */
    private TrabajosController() {
        thisController = this;
    }

    @Override
    public void actionPerformed(String event) {
        switch (event) {
            case "nuevo":
                nuevoTrabajo();
                break;
            case "borrar":
                borrarTrabajo();
                break;
            case "modificar":
                modificarTrabajo();
                break;
            case "show-trabajo":
                showTrabajo();
                break;
            case "deseleccionar-trabajo":
                TrabajosPane.getInstance().resetTrabajoClickeado();
                break;
            default:
                break;
        }
    }

    /**
     * Agregado de trabajo mediante input
     */
    private void nuevoTrabajo() {
        Trabajo nuevoTrabajo = inputTrabajo();

        if(nuevoTrabajo != null) {
            if(Main.isNombreValido(nuevoTrabajo.getNombre())) {

                if(nombreExists(nuevoTrabajo.getNombre()))
                    MessagesManager.showErrorAlert("Nombre existente");
                else {
                    RustiqueBDD.getInstance().insertarTrabajo(Trabajo.getGlobalId(),
                            nuevoTrabajo.getNombre(), nuevoTrabajo.getComentarios(), nuevoTrabajo.getFecha());

                    refreshData();
                }
            }
            else
                MessagesManager.showErrorAlert("Nombre invalido");
        }
    }

    /**
     * Permite realizar cambios en los parametros de un trabao
     */
    private void modificarTrabajo() {
        String nombre = TrabajosPane.getInstance().getTrabajoClickeado();
        Trabajo trabajoViejo;

        if(nombre == null) {
            if( (trabajoViejo = buscarTrabajo("Buscar trabajo a modificar")) == null)
                return;
        }
        else
            trabajoViejo = getTrabajoByNombre(nombre);

        if (trabajoViejo == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        CambiarModeloDialog cambiarModeloDialog = new CambiarModeloDialog(trabajoViejo);
        cambiarModeloDialog.show();

        Trabajo nuevoTrabajo = (Trabajo) cambiarModeloDialog.getResult();

        if(nuevoTrabajo == null)
            return;

        if(nombreExists(nuevoTrabajo.getNombre()) &&
                !nuevoTrabajo.getNombre().equalsIgnoreCase(trabajoViejo.getNombre())) {
            MessagesManager.showErrorAlert("NOMBRE EXISTENTE");
            return;
        }
        if(!Main.isNombreValido(nuevoTrabajo.getNombre())) {
            MessagesManager.showErrorAlert("NOMBRE INVALIDO");
            return;
        }

        RustiqueBDD.getInstance().cambiarTrabajo(trabajoViejo.getId(),
                nuevoTrabajo.getNombre(), nuevoTrabajo.getComentarios(), nuevoTrabajo.getFecha());
        refreshData();
        TrabajosPane.getInstance().resetTrabajoClickeado();
    }

    /**
     * Input de trabajo
     * @return trabajo ingresado
     */
    private Trabajo inputTrabajo() {
        NuevoModeloDialog nuevoModeloDialog = new NuevoModeloDialog("Nuevo trabajo", thisController);
        nuevoModeloDialog.show();
        return (Trabajo) nuevoModeloDialog.getResult();
    }

    /**
     * Getter de trabajos
     * @return lista de trabajos
     */
    public ObservableList<Trabajo> getData() {
        return data;
    }

    /**
     * Agregar trabajo a la lista
     * @param nuevoTrabajo nuevo trabajo
     */
    public static void addTrabajo(Trabajo nuevoTrabajo) {
        data.add(nuevoTrabajo);
    }

    /**
     * Indica si existe algun trabajo con el nombre ingresado
     * @param nombre nombre a saber si existe
     * @return true si existe el trabajo, caso contrario false
     */
    private boolean nombreExists(String nombre) {
        for(Trabajo trabajo : data)
            if(trabajo.getNombre().equalsIgnoreCase(nombre))
                return true;
        return false;
    }

    /**
     * Elimina todos los trabajos y los vuelve a cargar
     */
    private void refreshData() {
        data.clear();
        RustiqueBDD.getInstance().restoreTrabajosFromBDD();
    }

    /**
     * Input de Nombre o ID de trabajo
     * @return dato ingresado
     */
    private String inputTrabajoData(String titulo) {
        ModeloDataInputDialog modeloDataInputDialog = new ModeloDataInputDialog(titulo);
        modeloDataInputDialog.show();
        return modeloDataInputDialog.getResult();
    }
    /**
     * Borrado de trabajo
     */
    private void borrarTrabajo() {
        Trabajo trabajo;
        String nombre = TrabajosPane.getInstance().getTrabajoClickeado();

        if(nombre == null) {
            if( (trabajo = buscarTrabajo("Buscar trabajo a borrar")) == null)
                return;
        }
        else
            trabajo = getTrabajoByNombre(TrabajosPane.getInstance().getTrabajoClickeado());

        if(trabajo == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        if(MessagesManager.confirmation("Borrar trabajo " + trabajo.getNombre().toUpperCase()
                + " ?")) {
            data.remove(trabajo);
            RustiqueBDD.getInstance().deleteTrabajo(trabajo.getId());
            TrabajosPane.getInstance().resetTrabajoClickeado();
        }
    }

    /**
     * Muestra de parametros de trabajo
     */
    private void showTrabajo() {

        String nombre = TrabajosPane.getInstance().getTrabajoClickeado();
        Trabajo trabajo;

        if(nombre == null) {
            if ((trabajo = buscarTrabajo("Buscar trabajo")) == null)
                return;
        }
        else {
            trabajo = getTrabajoByNombre(nombre);
        }

        if(trabajo == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        ShowModeloDialog showModeloDialog = new ShowModeloDialog(trabajo);
        showModeloDialog.show();

        TrabajosPane.getInstance().resetTrabajoClickeado();
    }

    /**
     * Permite buscar un trabajo por nombre o por id
     * @return objeto Trabajo buscado
     */
    private Trabajo buscarTrabajo(String titulo) {
        String input = inputTrabajoData(titulo);
        Trabajo trabajoBuscado;

        if (input != null && !input.isEmpty()) {
            if (input.split("-")[0].equalsIgnoreCase("n")) {
                String nombre = input.split("-")[1];
                if (!nombreExists(nombre)) {
                    MessagesManager.showErrorAlert("Nombre no existente");
                    return null;
                }
                trabajoBuscado = getTrabajoByNombre(nombre);
            } else {
                if (Main.isNumeroValido(input.split("-")[1])) {
                    int id = Main.safeDecode(input.split("-")[1]);
                    if (!idExists(id)) {
                        MessagesManager.showErrorAlert("ID invalido");
                        return null;
                    }
                    trabajoBuscado = getTrabajoById(id);
                } else {
                    MessagesManager.showErrorAlert("Numero invalido");
                    return null;
                }
            }
        }
        else
            return null;

        if(trabajoBuscado == null) {
            MessagesManager.showFatalErrorAlert();
            return null;
        }

        TrabajosPane.getInstance().setTrabajoClickeado(trabajoBuscado.getNombre());
        return trabajoBuscado;
    }

    /**
     * Indica si existe algun trabajo con el id ingresado
     * @param id id a saber si existe
     * @return true si existe el trabajo, caso contrario false
     */
    private boolean idExists(int id) {
        for(Trabajo trabajo : data)
            if(trabajo.getId() == id)
                return true;
        return false;
    }

    /**
     * Devuelve el objeto Trabajo que buscamos por nombre
     * @param nombre nombre del trabajo buscado
     * @return trabajo buscado
     */
    private Trabajo getTrabajoByNombre(String nombre) {
        for(Trabajo trabajo : data)
            if(trabajo.getNombre().equalsIgnoreCase(nombre))
                return trabajo;
        return null;
    }

    /**
     * Devuelve el objeto Trabajo que buscamos por id
     * @param id id del trabajo buscado
     * @return trabajo buscado
     */
    private Trabajo getTrabajoById(int id) {
        for(Trabajo trabajo : data)
            if(trabajo.getId() == id)
                return trabajo;
        return null;
    }
}
