package rustique.models;

public class Cliente {

    private String nombre;
    private int saldo;
    private int id;

    public Cliente(String nombre, int saldo, int id) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.id = id;
    }

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
}
