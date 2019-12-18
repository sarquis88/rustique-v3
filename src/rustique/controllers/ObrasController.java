package rustique.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rustique.ImagesManager;
import rustique.Main;
import rustique.MessagesManager;
import rustique.bdd.RustiqueBDD;
import rustique.dialogs.*;
import rustique.models.Cliente;
import rustique.models.Obra;
import rustique.panes.ClientesPane;
import rustique.panes.ObrasPane;

public class ObrasController {

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
            case "borrar-obra":
                borrarObra();
                break;
            case "show-obra-clickeada":
                showObra(true);
                break;
            case "borrar-obra-clickeada":
                borrarObra();
                break;
            case "cambiar-obra":
                cambiarObra();
                break;
            default:
                break;
        }
    }

    /**
     * Agregado de obra mediante input
     */
    private void nuevaObra() {

        Obra obra = inputObra("Nueva obra");

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
        Obra obra = null;
        String nombre = ObrasPane.getInstance().getObraClickeada();

        if(nombre == null) {
            String input = inputObraData();

            if (input != null && !input.isBlank()) {

                if (input.split("-")[0].equalsIgnoreCase("n")) {
                    obra = getObraByNombre(input.split("-")[1]);
                    if (obra == null) {
                        MessagesManager.showErrorAlert("Nombre no existente");
                        return;
                    }
                } else {
                    if (Main.isNumeroValido(input.split("-")[1])) {
                        int id = Main.safeDecode(input.split("-")[1]);
                        obra = getObraById(id);
                        if (obra == null) {
                            MessagesManager.showErrorAlert("ID invalido");
                            return;
                        }
                    } else {
                        MessagesManager.showErrorAlert("Numero invalido");
                        return;
                    }
                }
            }
            else
                return;
        }
        else
            obra = getObraByNombre(ObrasPane.getInstance().getObraClickeada());

        if (obra == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        if(!MessagesManager.confirmation("Seguro desea borrar la obra " + obra.getNombre() + "?"))
            return;

        if(obra.getHasImage().equalsIgnoreCase("Si"))
            ImagesManager.removeImage(obra.getId());

        if (!RustiqueBDD.getInstance().deleteObra(obra.getId())) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        data.remove(obra);
        MessagesManager.showInformationAlert("Borrado: " + obra.getNombre().toUpperCase());
        ObrasPane.getInstance().resetObraClickeada();
    }

    /**
     * Input de obra
     * @return obra ingresada
     */
    private Obra inputObra(String titulo) {
        NuevaObraDialog nuevaObraDialog = new NuevaObraDialog(titulo);
        nuevaObraDialog.show();
        return nuevaObraDialog.getResult();
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
        ObraDataDialog obraDataDialog = new ObraDataDialog("Borrar obra");
        obraDataDialog.show();
        return obraDataDialog.getResult();
    }

    /**
     * Muestra de datos de la obra
     * @param clickeada true si la obra viene seleccionado por un click,
     * de lo contrario false
     */
    private void showObra(boolean clickeada) {

        String nombre = null;
        if(clickeada) {
            nombre = ObrasPane.getInstance().getObraClickeada();
        }
        else {
            String input = inputObraData();
            if (input != null && !input.isBlank()) {

                if (input.split("-")[0].equalsIgnoreCase("n")) {
                    nombre = input.split("-")[1];
                    if (!nombreExists(nombre)) {
                        MessagesManager.showErrorAlert("Nombre no existente");
                        return;
                    }
                } else {
                    if (Main.isNumeroValido(input.split("-")[1])) {
                        int id = Main.safeDecode(input.split("-")[1]);
                        if (idExists(id)) {
                            MessagesManager.showErrorAlert("ID invalido");
                            return;
                        }
                    } else {
                        MessagesManager.showErrorAlert("Numero invalido");
                        return;
                    }
                }
            }
            else
                return;
        }

        Obra obra = getObraByNombre(nombre);
        if(obra != null) {
            ShowObraDialog showObraDialog = new ShowObraDialog(obra);
            showObraDialog.show();
        }
        else
            MessagesManager.showFatalErrorAlert();
    }

    /**
     * Permite cambiar las caracteristicas de una obra
     */
    private void cambiarObra() {
        Obra obraVieja = getObraByNombre(ObrasPane.getInstance().getObraClickeada());
        if(obraVieja == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }

        CambiarObraDialog cambiarObraDialog = new CambiarObraDialog(obraVieja);
        cambiarObraDialog.show();
        Obra obra = cambiarObraDialog.getResult();

        if(obra == null)		// cancel
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
    }
}
