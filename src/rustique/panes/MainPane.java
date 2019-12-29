package rustique.panes;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import rustique.misc.ImagesManager;
import rustique.misc.RustiqueParameters;

public class MainPane extends RustiquePane implements RustiqueParameters {

    private static MainPane thisMainPane = null;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static MainPane getInstance() {
        if (thisMainPane == null)
            thisMainPane = new MainPane();
        return thisMainPane;
    }

    /**
     * Constructor de clase
     */
    private MainPane() {
        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);
        String imagesPath = "./src/images/";

        ImageView img0 = ImagesManager.getImageView(imagesPath + "setUpImage0.jpg", false);
        if(img0 != null) {
            img0.setLayoutY(0);
            img0.setLayoutX(hPadding);
        }

        ImageView img1 = ImagesManager.getImageView(imagesPath + "setUpImage1.jpg", false);
        if(img1 != null) {
            img1.setLayoutY(thisPane.getPrefHeight() - img1.getFitHeight());
            img1.setLayoutX(thisPane.getPrefWidth() - img1.getFitWidth());
        }

        ImageView img2 = ImagesManager.getImageView(imagesPath + "setUpImage2.jpg", false);
        if(img2 != null) {
            img2.setLayoutY(0);
            img2.setLayoutX(thisPane.getPrefWidth() - img2.getFitWidth());
        }

        ImageView img3 = ImagesManager.getImageView(imagesPath + "setUpImage3.jpg", false);
        if(img3 != null) {
            img3.setLayoutY(thisPane.getPrefHeight() - img3.getFitHeight());
            img3.setLayoutX(hPadding);
        }

        ImageView img4 = ImagesManager.getImageView(imagesPath + "mainImage.png", false);
        if(img4 != null) {
            img4.setLayoutY(screenHeight / 2 - img4.getFitHeight() / 2);
            img4.setLayoutX((thisPane.getPrefWidth() - img4.getFitWidth()) / 2);
        }

        thisPane.getChildren().addAll(img0, img1, img2, img3, img4);
    }
}
