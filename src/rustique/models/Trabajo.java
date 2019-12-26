package rustique.models;

import java.util.ArrayList;

public class Trabajo implements Modelo {

    private static int globalId = 0;

    private String nombre;
    private String comentarios;
    private int id;
    public ArrayList<String> datos;

    public Trabajo() {}

    public Trabajo(String nombre, String comentarios, int id) {
        this.nombre = nombre;
        this.comentarios = comentarios;
        this.id = id;
        refreshDatos();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void refreshDatos() {
        this.datos = new ArrayList<>();
        this.datos.add("Nombre");
        this.datos.add(this.nombre);
        this.datos.add("Comentarios");
        this.datos.add(this.comentarios);
        this.datos.add("ID");
        this.datos.add(String.valueOf(this.id));
    }

    @Override
    public ArrayList<String> getDatos() {
        return this.datos;
    }

    public static int getGlobalId() {
        return globalId;
    }

    public static void setGlobalId(int globalId) {
        Trabajo.globalId = globalId;
    }
}
