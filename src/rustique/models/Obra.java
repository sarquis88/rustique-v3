package rustique.models;

import java.util.ArrayList;

public class Obra implements Modelo {

    private static int globalId = 0;

    private String nombre;
    private String autor;
    private String tipo;
    private String tamanio;
    private int precio;
    private int id;
    private String hasImage;
    public ArrayList<String> datos;

    public Obra(String nombre, String autor, String tipo, String tamanio,
                int precio, int id, String hasImage) {
        this.nombre = nombre;
        this.precio = precio;
        this.autor = autor;
        this.tamanio = tamanio;
        this.tipo = tipo;
        this.id = id;
        this.hasImage = hasImage;

        refreshDatos();
    }

    public Obra() {}

    public String getNombre() {
        return nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getGlobalId() {
        return globalId;
    }

    public static void setGlobalId(int globalId) {
        Obra.globalId = globalId;
    }

    public String getHasImage() {
        return hasImage;
    }

    public void setHasImage(String hasImage) {
        this.hasImage = hasImage;
    }

    @Override
    public void refreshDatos() {
        this.datos = new ArrayList<>();
        this.datos.add("Nombre");
        this.datos.add(this.nombre);
        this.datos.add("Autor");
        this.datos.add(this.autor);
        this.datos.add("Precio");
        this.datos.add(String.valueOf(this.precio));
        this.datos.add("Tipo");
        this.datos.add(this.tipo);
        this.datos.add("Tamaño");
        this.datos.add(this.tamanio);
        this.datos.add("ID");
        this.datos.add(String.valueOf(this.id));
        this.datos.add("Imágen");
        this.datos.add(this.hasImage);
    }

    @Override
    public ArrayList<String> getDatos() {
        return this.datos;
    }

    public String getImgNombre() {
        return (    this.nombre + " - " +
                    this.autor + " - " +
                    this.tipo + " - " +
                    this.tamanio + " - " +
                    "$" + this.precio   );
    }
}
