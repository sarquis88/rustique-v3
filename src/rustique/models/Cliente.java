package rustique.models;

import java.util.ArrayList;

public class Cliente implements Modelo {

    private static int globalId = 0;

    private String nombre;
    private int saldo;
    private int id;
    private String comentarios;
    public ArrayList<String> datos;

    public Cliente(String nombre, int saldo, int id, String comentarios) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.id = id;
        this.comentarios = comentarios;
        refreshDatos();
    }

    public Cliente() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public static int getGlobalId() {
        return globalId;
    }

    public static void setGlobalId(int globalId) {
        Cliente.globalId = globalId;
    }

    @Override
    public void refreshDatos() {
        this.datos = new ArrayList<>();
        this.datos.add("Nombre");
        this.datos.add(this.nombre);
        this.datos.add("Saldo");
        this.datos.add(String.valueOf(this.saldo));
        this.datos.add("Comentarios");
        this.datos.add(this.comentarios);
        this.datos.add("ID");
        this.datos.add(String.valueOf(this.id));
    }

    @Override
    public ArrayList<String> getDatos() {
        return this.datos;
    }
}
