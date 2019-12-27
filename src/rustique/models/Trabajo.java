package rustique.models;

import java.util.ArrayList;

public class Trabajo implements Modelo {

    private static int globalId = 0;

    private String cliente;
    private String fecha;
    private String comentarios;
    private int id;
    public ArrayList<String> datos;

    public Trabajo() {}

    public Trabajo(String cliente, String comentarios, String fecha, int id) {
        this.cliente = cliente;
        this.comentarios = comentarios;
        this.id = id;
        this.fecha = fecha;
        refreshDatos();
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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
        this.datos.add("Cliente");
        this.datos.add(this.cliente);
        this.datos.add("Comentarios");
        this.datos.add(this.comentarios);
        this.datos.add("Fecha");
        this.datos.add(this.fecha);
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
