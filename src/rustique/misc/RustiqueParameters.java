package rustique.misc;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public interface RustiqueParameters {

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    double screenHeight = screenBounds.getHeight() - 55;  // (- 65) : barras superiores horizontales
    double screenWidth = screenBounds.getWidth();

    double buttonsHeight = 30.00;
    double buttonsWidth = 190.00;

    double hPadding = 10;
    double vPadding = 5;

    double sepLayoutX = screenWidth * 0.15;
    double sepWidth = 5.0;

    String tituloStyle = "-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 35px;";
    String buttonsStyle = "-fx-font-size: 15;";
    String separatorStyle = "-fx-background-color: red;";
    String tableColumnsStyle = "-fx-alignment: CENTER;";

    int comentMaxSize = 40;

    String obrasPath = "./src/images/obras/";
    String bddPath = "./src/rustique/bdd/rustique.db";
}
