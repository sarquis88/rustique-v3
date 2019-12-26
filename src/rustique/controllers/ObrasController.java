package rustique.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rustique.misc.ImagesManager;
import rustique.Main;
import rustique.misc.MessagesManager;
import rustique.bdd.RustiqueBDD;
import rustique.dialogs.*;
import rustique.models.Obra;
import rustique.panes.ObrasPane;

public class ObrasController implements Controller {

    private static ObrasController thisController = null;
    private static ObservableList<Obra> data = FXCollections.observableArrayList();

    public static ObrasController getInstance() {
        if(thisController == null)
            thisController = new ObrasController();
        return thisController;
    }

    private ObrasController() {}

    /**
     * Respuesta a eventos
     * @param event tipo de evento
     */
    public void actionPerformed(String event) {
        switch (event) {
            case "nueva-obra":
                nuevaObra();
                break;
            case "borrar":
                borrarObra();
                break;
            case "show-obra":
                showObra();
                break;
            case "modificar":
                cambiarObra();
                break;
            case "ver-foto":
                verFoto();
                break;
            case "ver-fotos":
                verFotos();
                break;
            case "borrar-foto":
                borrarFoto();
                break;
            case "agregar-foto":
                agregarFoto();
                break;
            case "deseleccionar-obra":
                deseleccionarObra();
                break;
            default:
                break;
        }
    }

    /**
     * Agregado de obra mediante input
     */
    private void nuevaObra() {
        Obra obra = inputObra();

        if (obra != null) {
            if (!Main.isNombreValido(obra.getNombre())) {
                MessagesManager.showErrorAlert("NOMBRE INVALIDO");
                return;
            }
            if (nombreExists(obra.getNombre())) {
                MessagesManager.showErrorAlert("NOMBRE EXISTENTE");
                return;
            }

            if (isBlank(obra.getAutor()))
                obra.setAutor("Sin autor");
            if (isBlank(obra.getTipo()))
                obra.setTipo("Sin tipo");
            if (isBlank(obra.getTamanio()))
                obra.setTamanio("Sin tamaño");


            if (MessagesManager.confirmation("Desea agregar una foto?"))
                obra.setHasImage(inputImage(Obra.getGlobalId()));
            else
                obra.setHasImage("No");

            RustiqueBDD.getInstance().insertarObra(Obra.getGlobalId(),
                    obra.getNombre(), obra.getAutor(), obra.getTipo(),
                    obra.getTamanio(), obra.getPrecio(), obra.getHasImage());
            refreshData();

            MessagesManager.showInformationAlert("Obra agregada: " + obra.getNombre());
        }
    }

    /**
     * Borrado de obra
     */
    private void borrarObra() {
        String nombre = ObrasPane.getInstance().getObraClickeada();
        Obra obra;

        if(nombre == null) {
            if ( (obra = buscarObra()) == null)
                return;
        }
        else
            obra = getObraByNombre(ObrasPane.getInstance().getObraClickeada());

        if (obra == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        if(MessagesManager.confirmation("Seguro desea borrar la obra " + obra.getNombre() + "?")) {
            if(obra.getHasImage().equalsIgnoreCase("Si"))
                ImagesManager.removeImage(obra.getId());
            RustiqueBDD.getInstance().deleteObra(obra.getId());
            data.remove(obra);
            MessagesManager.showInformationAlert("Borrado: " + obra.getNombre().toUpperCase());
            deseleccionarObra();
        }
    }

    /**
     * Input de obra
     * @return obra ingresada
     */
    private Obra inputObra() {
        NuevoModeloDialog nuevoModeloDialog = new NuevoModeloDialog("Nueva obra", thisController);
        nuevoModeloDialog.show();
        return (Obra) nuevoModeloDialog.getResult();
    }

    /**
     * Devuelve nombre de obra de acuerdo al id ingresado
     * @param id de obra
     * @return nombre de obra buscada
     */
    public String getObraNameById(int id) {
        for(Obra obra : data)
            if(obra.getId() == id)
                return obra.getNombre();
        return ("Obra " + id);
    }

    /**
     * Devuelve el objeto Obra que buscamos por nombre
     * @param nombre nombre de la obra buscada
     * @return obra buscado
     */
    private Obra getObraByNombre(String nombre) {
        for(Obra obra : data)
            if(obra.getNombre().equalsIgnoreCase(nombre))
                return obra;
        return null;
    }

    /**
     * Devuelve el objeto Obra que buscamos por id
     * @param id id de la obra buscada
     * @return obra buscada
     */
    private Obra getObraById(int id) {
        for(Obra obra : data)
            if(obra.getId() == id)
                return obra;
        return null;
    }

    /**
     * Indica si existe alguna obra con el nombre ingresado
     * @param nombre nombre a saber si existe
     * @return true si existe la obra, caso contrario false
     */
    private boolean nombreExists(String nombre) {
        for(Obra obra : data)
            if(obra.getNombre().equalsIgnoreCase(nombre))
                return true;
        return false;
    }

    /**
     * Indica si existe alguna obra con el id ingresado
     * @param id id a saber si existe
     * @return true si existe la obra, caso contrario false
     */
    private boolean idExists(int id) {
        for(Obra obra : data)
            if(obra.getId() == id)
                return true;
        return false;
    }

    /**
     * Getter de obras
     * @return lista de obras
     */
    public ObservableList<Obra> getData() {
        return data;
    }

    /**
     * Agregar obra a la lista
     * @param nuevaObra nueva obra
     */
    public static void addObra(Obra nuevaObra) {
        data.add(nuevaObra);
    }

    /**
     * Alternativa a isBlank() del jdk13
     * @param texto texto a analizar
     * @return true si no esta vacio, de lo contrario false
     */
    private boolean isBlank(String texto) {
        return texto == null || texto.isEmpty() || texto.charAt(0) == ' ';
    }

    /**
     * Elimina todas las obras y las vuelve a cargar
     */
    private void refreshData() {
        data.clear();
        RustiqueBDD.getInstance().restoreObrasFromBDD();
    }

    /**
     * Muestra de dialogo para insertar imagen
     * @param idObra id de obra correspondiente a la imagen
     * @return "Si" si se insertó la imagen, de lo contrario "No"
     */
    public String inputImage(int idObra) {
        NuevaImagenDialog nuevaImagenDialog = new NuevaImagenDialog();
        return nuevaImagenDialog.show(idObra, false);
    }

    /**
     * Input de Nombre o ID de obra
     * @return dato ingresado
     */
    private String inputObraData() {
        ModeloDataInputDialog modeloDataInputDialog = new ModeloDataInputDialog("Borrar obra");
        modeloDataInputDialog.show();
        return modeloDataInputDialog.getResult();
    }

    /**
     * Muestra de datos de la obra
     * de lo contrario false
     */
    private void showObra() {
        String nombre = ObrasPane.getInstance().getObraClickeada();
        Obra obra;

        if(nombre == null) {
            if ( (obra = buscarObra()) == null)
                return;
        }
        else
            obra = getObraByNombre(nombre);

        if(obra != null) {
            ShowModeloDialog showModeloDialog = new ShowModeloDialog(obra);
            showModeloDialog.show();
            ObrasPane.getInstance().resetObraClickeada();
        }
        else
            MessagesManager.showFatalErrorAlert();
    }

    /**
     * Permite cambiar las caracteristicas de una obra
     */
    private void cambiarObra() {
        String nombre = ObrasPane.getInstance().getObraClickeada();
        Obra obraVieja;

        if(nombre == null) {
            if ( (obraVieja = buscarObra()) == null)
                return;
        }
        else
            obraVieja = getObraByNombre(nombre);

        if(obraVieja == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        CambiarModeloDialog cambiarModeloDialog = new CambiarModeloDialog(obraVieja);
        cambiarModeloDialog.show();

        Obra obra = (Obra) cambiarModeloDialog.getResult();

        if(obra == null)
            return;

        if(nombreExists(obra.getNombre()) &&
                !obra.getNombre().equalsIgnoreCase(obraVieja.getNombre())) {
            MessagesManager.showErrorAlert("NOMBRE EXISTENTE");
            return;
        }

        if(isBlank(obra.getAutor()))
            obra.setAutor("Sin autor");
        if(isBlank(obra.getTipo()))
            obra.setTipo("Sin tipo");
        if(isBlank(obra.getTamanio()))
            obra.setTamanio("Sin tamaño");
        int idViejo = obraVieja.getId();

        obra.setHasImage(obraVieja.getHasImage());

        RustiqueBDD.getInstance().cambiarObra(idViejo,
                obra.getNombre(), obra.getAutor(), obra.getTipo(),
                obra.getTamanio(), obra.getPrecio(), obra.getHasImage());
        refreshData();

        deseleccionarObra();
    }

    /**
     * Permite buscar una obra por nombre o por id
     * @return objeto Obra buscada
     */
    private Obra buscarObra() {
        String input = inputObraData();
        Obra obraBuscada;

        if (input != null && !input.isEmpty()) {
            if (input.split("-")[0].equalsIgnoreCase("n")) {
                String nombre = input.split("-")[1];
                if (!nombreExists(nombre)) {
                    MessagesManager.showErrorAlert("Nombre no existente");
                    return null;
                }
                obraBuscada = getObraByNombre(nombre);
            } else {
                if (Main.isNumeroValido(input.split("-")[1])) {
                    int id = Main.safeDecode(input.split("-")[1]);
                    if (!idExists(id)) {
                        MessagesManager.showErrorAlert("ID invalido");
                        return null;
                    }
                    obraBuscada = getObraById(id);
                } else {
                    MessagesManager.showErrorAlert("Numero invalido");
                    return null;
                }
            }
        }
        else
            return null;

        if(obraBuscada == null) {
            MessagesManager.showFatalErrorAlert();
            return null;
        }
        ObrasPane.getInstance().setObraClickeada(obraBuscada.getNombre());

        return obraBuscada;
    }

    /**
     * Muestra de foto
     */
    private void verFoto() {
        String nombre = ObrasPane.getInstance().getObraClickeada();
        Obra obra = getObraByNombre(nombre);

        if (obra != null) {
            ShowImagenDialog showImagenDialog = new ShowImagenDialog();
            showImagenDialog.show(obra.getId());
        }
        else
            MessagesManager.showFatalErrorAlert();
    }

    /**
     * Borrado de foto
     */
    private void borrarFoto() {
        String nombre = ObrasPane.getInstance().getObraClickeada();

        if(nombre == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        Obra obra = getObraByNombre(nombre);

        if(obra == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        ImagesManager.removeImage(obra.getId());
        obra.setHasImage("No");

        RustiqueBDD.getInstance().cambiarObra(obra.getId(),
                obra.getNombre(), obra.getAutor(), obra.getTipo(),
                obra.getTamanio(), obra.getPrecio(), obra.getHasImage());
        refreshData();
    }

    /**
     * Agregar foto a determinada obra
     */
    private void agregarFoto() {
        Obra obra = getObraByNombre(ObrasPane.getInstance().getObraClickeada());

        if(obra == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        obra.setHasImage(inputImage(obra.getId()));

        RustiqueBDD.getInstance().cambiarObra(obra.getId(),
                obra.getNombre(), obra.getAutor(), obra.getTipo(),
                obra.getTamanio(), obra.getPrecio(), obra.getHasImage());
        refreshData();
    }

    private void verFotos() {
        ShowImagenDialog showImagenDialog = new ShowImagenDialog();
        showImagenDialog.show(-1);
    }

    private void deseleccionarObra() {
        ObrasPane.getInstance().resetObraClickeada();
    }
}
