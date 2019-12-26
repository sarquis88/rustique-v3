package rustique;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rustique.misc.MessagesManager;
import rustique.misc.View;
import rustique.bdd.RustiqueBDD;

public class Main extends Application {

    private static Stage window;

    /**
     * Funcion main
     * @param args argumentos de ejecucion
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Retorno de Stage principal
     * @return objeto Stage de vista
     */
    public static Stage getWindow() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        window.setResizable(false);
        window.setMaximized(true);
        window.setTitle("Rustique - Galeria de arte y taller de enmarcado");
        window.getIcons().add(new Image("file:./src/images/logo.png"));

        RustiqueBDD.getInstance();
        window.setScene(View.getInstance().getScene());
        window.show();
    }

    /**
     * Validacion de numero
     * @param numero string correspondiente al numero
     * @return true si el numero es valido, de lo contrario false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNumeroValido(String numero) {
        if(numero.length() == 0)
            return false;

        for(int i = 0; i < numero.length(); i++) {
            boolean valido = false;
            char c = numero.charAt(i);
            for(int j = 0; j < 10; j++) {
                char aux = (char) j;
                if(c == aux + 48) {
                    valido = true;
                    break;
                }
            }
            if(!valido)
                return false;
        }
        return true;
    }

    /**
     * Validacion de nombre
     * @param nombre string correspondiente al nombre
     * @return true si el nombre es valido, de lo contrario false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNombreValido(String nombre) {
        return nombre != null && !nombre.isEmpty() && nombre.charAt(0) != 32;
    }

    /**
     * Pasaje de string a int
     * @param number string a convertir
     * @return string convertido en int
     */
    public static int safeDecode(String number) {
        try {
            return Integer.decode(number);
        }
        catch (NumberFormatException e) {
            MessagesManager.showErrorAlert("Número demasiado grande. Se truncó.");
            return 999999999;
        }
    }

}