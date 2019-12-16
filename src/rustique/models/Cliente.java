package rustique.models;

public class Cliente {

    private static int globalId = 0;

    private String nombre;
    private int saldo;
    private int id;
    private String comentarios;

    public Cliente(String nombre, int saldo, int id, String comentarios) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.id = id;
        this.comentarios = comentarios;
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

    public static void sumGlobalId() {
        globalId++;
    }
}
