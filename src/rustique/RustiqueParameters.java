package rustique;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public interface RustiqueParameters {

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    double screenHeight = screenBounds.getHeight() - 65;  // (- 65) : barras superiores horizontales
    double screenWidth = screenBounds.getWidth();

    double hPadding = 10;
    double vPadding = 5;

    double sepLayoutX = screenWidth * 0.15;
    double sepWidth = 5.0;

}
