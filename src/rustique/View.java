package rustique;

import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class View implements RParameters {

    private static View thisView = null;
    private Controller thisController;
    private Scene thisScene;

    private double screenWidth;
    private double screenHeight;

    /**
     * Patron singleton
     * @param controller controlador de eventos
     * @return instancia unica de clase
     */
    public static View getInstance(Controller controller) {
        if(thisView == null)
            thisView = new View(controller);
        return thisView;
    }

    /**
     * Constructor de clase
     * @param controller de eventos
     */
    private View(Controller controller) {
        this.thisController = controller;

        String imagesPath = "./src/images/";

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        this.screenHeight = screenBounds.getHeight() - 65;  // (- 65) : barras superiores horizontales
        this.screenWidth = screenBounds.getWidth();

        Separator sep0 = new Separator();
        sep0.setOrientation(Orientation.VERTICAL);
        sep0.setLayoutX(screenWidth * 0.15);
        sep0.setLayoutY(0);
        sep0.setPrefWidth(5);
        sep0.setPrefHeight(2000);
        sep0.setStyle("-fx-background-color: red;");

        ImageView img0 = ImagesManager.getImageView(imagesPath + "setUpImage0.jpg");
        if(img0 != null) {
            img0.setLayoutY(0);
            img0.setLayoutX(sep0.getLayoutX() + sep0.getPrefWidth() + hPadding);
        }

        ImageView img1 = ImagesManager.getImageView(imagesPath + "setUpImage1.jpg");
        if(img1 != null) {
            img1.setLayoutY(screenHeight - img1.getFitHeight());
            img1.setLayoutX(screenWidth - img1.getFitWidth());
        }

        ImageView img2 = ImagesManager.getImageView(imagesPath + "setUpImage2.jpg");
        if(img2 != null) {
            img2.setLayoutY(0);
            img2.setLayoutX(screenWidth - img2.getFitWidth());
        }

        ImageView img3 = ImagesManager.getImageView(imagesPath + "setUpImage3.jpg");
        if(img3 != null) {
            img3.setLayoutY(screenHeight - img3.getFitHeight());
            img3.setLayoutX(sep0.getLayoutX() + sep0.getPrefWidth() + hPadding);
        }

        ImageView img4 = ImagesManager.getImageView(imagesPath + "mainImage.png");
        if(img4 != null) {
            img4.setLayoutY(screenHeight / 2 - img4.getFitHeight() / 2);
            img4.setLayoutX(sep0.getLayoutX() + ((screenWidth -
                    sep0.getLayoutX()) / 2) - img4.getFitWidth() / 2);
        }

        Grid grid = new Grid(thisController);
        grid.setLayout( (sep0.getLayoutX() / 2) -
                (grid.getGridPane().getPrefWidth() / 2), 0);
        grid.setDisable("principal", true);

        Pane layout = new Pane();

        layout.setStyle("-fx-background-color: gray;");
        layout.getChildren().addAll(grid.getGridPane(), sep0, img0, img1, img2, img3, img4);

        this.thisScene = new Scene(layout);
    }

    /**
     * Devuelve ventana de vosta
     * @return objeto Scene
     */
    public Scene getScene() {
        return this.thisScene;
    }

    /**
     * Getter ancho de pantalla
     * @return screen width en formato double
     */
    public double getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * Getter alto de pantalla
     * @return screen height en formato double
     */
    public double getScreenHeight() {
        return this.screenHeight;
    }
}
