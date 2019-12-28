package rustique.bdd;

import rustique.controllers.ClientesController;
import rustique.controllers.ObrasController;
import rustique.controllers.TrabajosController;
import rustique.misc.RustiqueParameters;
import rustique.models.Cliente;
import rustique.models.Obra;
import rustique.models.Trabajo;

import java.sql.*;

public class RustiqueBDD implements RustiqueParameters {

    private static RustiqueBDD thisBDD = null;
    private static Statement stmt = null;
    private static Connection c = null;

    /**
     * Patron Singleton
     * @return instancia unica de RustiqueBDD
     */
    public static RustiqueBDD getInstance() {
        if(thisBDD == null)
            thisBDD = new RustiqueBDD();
        return thisBDD;
    }

    /**
     * Constructor de clase.
     */
    private RustiqueBDD() {
        createTables();
        restoreClientesFromBDD();
        restoreObrasFromBDD();
        restoreTrabajosFromBDD();
    }

    /**
     * Creacion de tablas correspondientes a la base de
     * datos, solamente si no están ya creadas
     */
    private void createTables() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            c.setAutoCommit(true);
            stmt = c.createStatement();

            if(!tableExists("CLIENTES")) {
                String sql = 	"CREATE TABLE CLIENTES " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " NOMBRE         TEXT    NOT NULL, " +
                        " SALDO			 INT	 NOT NULL, " +
                        " COMENTARIOS    TEXT    NOT NULL) ";
                stmt.executeUpdate(sql);



                stmt.close();
            }
            if(!tableExists("OBRAS")) {
                String sql = 	"CREATE TABLE OBRAS " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " NOMBRE         TEXT    NOT NULL, " +
                        " AUTOR         TEXT    NOT NULL, " +
                        " TIPO 	         TEXT    NOT NULL, " +
                        " TAMAÑO         TEXT    NOT NULL, " +
                        " PRECIO          INT    NOT NULL, " +
                        " IMAGEN         TEXT    NOT NULL) ";
                stmt.executeUpdate(sql);
                stmt.close();
            }
            if(!tableExists("TRABAJOS")) {
                String sql = 	"CREATE TABLE TRABAJOS " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " CLIENTE        TEXT    NOT NULL, " +
                        " COMENTARIOS    TEXT    NOT NULL, " +
                        " FECHA          TEXT    NOT NULL) ";
                stmt.executeUpdate(sql);
                stmt.close();
            }
            if(!tableExists("UTILIDADES")) {
                String sql = 	"CREATE TABLE UTILIDADES " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " COLOR       INT        NOT NULL," +
                        " INITIALDIR  TEXT       NOT NULL)";
                stmt.executeUpdate(sql);

                sql = "INSERT INTO UTILIDADES (ID,COLOR,INITIALDIR) " +
                        "VALUES (" + 0 + ", " + 7 + ", " + "'./'" + " );";
                stmt.executeUpdate(sql);

                stmt.close();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Chequea si la tabla existe en la base de datos
     * @param tableName nombre de tabla
     * @return true si la tabla existe, de lo contrario false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean tableExists(String tableName) {
        boolean bool = false;
        try {
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);

            if(rs.next())
                bool = true;

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * Insercion de cliente en base de datos
     * @param id identificador del cliente
     * @param nombre nombre del cliente
     * @param saldo saldo del cliente
     * @param comentarios comentarios del cliente
     */
    public void insertarCliente(int id, String nombre, int saldo,
                                String comentarios) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "INSERT INTO CLIENTES (ID,NOMBRE,SALDO, COMENTARIOS) " +
                    "VALUES (" + id + ", '" + nombre + "', " +
                    saldo + ", '" + comentarios + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Eliminacion de cliente en base de datos
     * @param id identificador del cliente
     */
    public void deleteCliente(int id) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "DELETE from CLIENTES where ID=" + id + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambiar datos de cliente
     * @param nombreActual nombre actual del cliente a cambiar
     * @param nombreNuevo nombre nuevo a insertar
     * @param saldoNuevo saldo nuevo a insertar
     * @param comentNuevo comentario nuevo a insertar
     */
    public void cambiarCliente(String nombreActual, String nombreNuevo, int saldoNuevo,
                               String comentNuevo) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "UPDATE CLIENTES SET SALDO=" + saldoNuevo +
                    " WHERE NOMBRE='" + nombreActual + "'";
            stmt.executeUpdate(sql);

            sql = "UPDATE CLIENTES SET COMENTARIOS='" + comentNuevo +
                    "' WHERE NOMBRE='" + nombreActual + "'";
            stmt.executeUpdate(sql);

            sql = "UPDATE CLIENTES SET NOMBRE='" + nombreNuevo +
                    "' WHERE NOMBRE='" + nombreActual + "'";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lectura de la base de datos y creacion de objetos java correspondiente
     * a los clientes. Metodo llamado al comienzo de la ejecucion, y cada vez
     * que se agrega un cliente.
     */
    public void restoreClientesFromBDD() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM CLIENTES;");

            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                String comentarios = rs.getString("COMENTARIOS");
                int saldo = rs.getInt("SALDO");
                int id = rs.getInt("ID");

                ClientesController.addCliente(
                        new Cliente(nombre, saldo, id, comentarios));
                Cliente.setGlobalId(id + 1);
            }
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insercion de obra en base de datos
     * @param id identificador de la obra
     * @param nombre nombre de la obra
     * @param autor autor de la obra
     * @param tipo tipo de obra
     * @param tamanio tamanio fisico de obra
     * @param precio precio de obra
     * @param hasImage indica si la obra tiene o no una imagen asociada
     */
    public void insertarObra(int id, String nombre, String autor, String tipo,
                             String tamanio, int precio, String hasImage) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "INSERT INTO OBRAS (ID,NOMBRE,AUTOR,TIPO,TAMAÑO,PRECIO,IMAGEN) " +
                    "VALUES (" + id + ", '" + nombre + "', '" + autor + "', '" + tipo +
                    "', '" + tamanio + "', " + precio + ", '" + hasImage + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Eliminacion de obra en base de datos
     * @param id identificador de la obra
     */
    public void deleteObra(int id) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "DELETE from OBRAS where ID=" + id + ";";

            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambiar datos de la obra
     * @param id id actual de la obra a cambiar
     * @param nombreNuevo nombre nuevo a insertar
     * @param autorNuevo nuevo autor a insertar
     * @param tipoNuevo tipo de obra a insertar
     * @param tamanioNuevo tamaño a insertar
     * @param precioNuevo precio a insertar
     */
    public void cambiarObra(int id, String nombreNuevo, String autorNuevo,
                            String tipoNuevo, String tamanioNuevo, int precioNuevo,
                            String hasImageNuevo) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "UPDATE OBRAS SET AUTOR='" + autorNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE OBRAS SET TIPO='" + tipoNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE OBRAS SET TAMAÑO='" + tamanioNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE OBRAS SET PRECIO=" + precioNuevo +
                    " WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE OBRAS SET IMAGEN='" + hasImageNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE OBRAS SET NOMBRE='" + nombreNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lectura de la base de datos y creacion de objetos java correspondiente
     * a las obras. Metodo llamado al comienzo de la ejecucion, y cada vez
     * que se agrega una obra.
     */
    public void restoreObrasFromBDD() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM OBRAS;");

            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                String autor = rs.getString("AUTOR");
                String tipo = rs.getString("TIPO");
                String tamanio = rs.getString("TAMAÑO");
                int precio = rs.getInt("PRECIO");
                String hasImage = rs.getString("IMAGEN");
                int id = rs.getInt("ID");

                ObrasController.addObra(
                        new Obra(nombre, autor, tipo, tamanio,
                                precio, id, hasImage));
                Obra.setGlobalId(id + 1);
            }
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insercion de trabajo en base de datos
     * @param id identificador del trabajo
     * @param cliente nombre del trabajo
     * @param comentarios comentarios del trabajo
     */
    public void insertarTrabajo(int id, String cliente, String comentarios, String fecha) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "INSERT INTO TRABAJOS (ID,CLIENTE,COMENTARIOS, FECHA) " +
                    "VALUES (" + id + ", '" + cliente + "', '" + comentarios + "', '" + fecha + "' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Eliminacion de trabajo en base de datos
     * @param id identificador del trabajo
     */
    public void deleteTrabajo(int id) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "DELETE from TRABAJOS where ID=" + id + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lectura de la base de datos y creacion de objetos java correspondiente
     * a los trabajos. Metodo llamado al comienzo de la ejecucion, y cada vez
     * que se agrega un trabajo.
     */
    public void restoreTrabajosFromBDD() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM TRABAJOS;");

            while (rs.next()) {
                String cliente = rs.getString("CLIENTE");
                String comentarios = rs.getString("COMENTARIOS");
                String fecha = rs.getString("FECHA");
                int id = rs.getInt("ID");

                TrabajosController.addTrabajo(
                        new Trabajo(cliente, comentarios, fecha, id));
                Trabajo.setGlobalId(id + 1);
            }
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambiar datos del trabajo
     * @param id id actual del trabajo a cambiar
     * @param clienteNuevo cliente nombre nuevo a insertar
     * @param comentariosNuevo comentario nuevo a insertar
     * @param fechaNueva fecha nueva a insertar
     */
    public void cambiarTrabajo(int id, String clienteNuevo, String comentariosNuevo, String fechaNueva) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "UPDATE TRABAJOS SET COMENTARIOS='" + comentariosNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE TRABAJOS SET CLIENTE='" + clienteNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE TRABAJOS SET FECHA='" + fechaNueva +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restablecer la base de datos
     * Borrado de todos los datos
     */
    public void restablecerBDD() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "DROP TABLE CLIENTES;";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE OBRAS;";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE TRABAJOS;";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE UTILIDADES;";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambiar color de default de fondo
     * @param color numero correspondiente al color
     */
    public void insertarColor(int color) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "UPDATE UTILIDADES SET COLOR='" + color +
                    "' WHERE ID=0";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter de color de default de fondo
     * @return int correspondiente al color
     */
    public int getColor() {
        int color = 0;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "SELECT COLOR FROM UTILIDADES WHERE ID=0;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                color = rs.getInt("COLOR");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return color;
    }

    /**
     * Cambiar directorio inicial de default
     * @param dir string correspondiente al directorio inicial
     */
    public void insertarDirInicial(String dir) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "UPDATE UTILIDADES SET INITIALDIR='" + dir +
                    "' WHERE ID=0";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter de path inicial de default
     * @return String correspondiente al path
     */
    public String getInitialDir() {
        String dir = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "SELECT INITIALDIR FROM UTILIDADES WHERE ID=0;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                dir = rs.getString("INITIALDIR");
            }

            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dir;
    }
}
