package rustique.models;

public class Obra {

    private static int globalId = 0;

    private String nombre;
    private String autor;
    private String tipo;
    private String tamanio;
    private int precio;
    private int id;
    private String hasImage;

    public Obra(String nombre, String autor, String tipo, String tamanio,
                int precio, int id, String hasImage) {
        this.nombre = nombre;
        this.precio = precio;
        this.autor = autor;
        this.tamanio = tamanio;
        this.tipo = tipo;
        this.id = id;
        this.hasImage = hasImage;
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
}
