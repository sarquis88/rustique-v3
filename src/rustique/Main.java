package rustique;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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

        window.setScene(Controller.getInstance().getScene());
        window.show();
    }
}