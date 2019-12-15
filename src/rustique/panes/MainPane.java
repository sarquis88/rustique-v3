package rustique.panes;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import rustique.ImagesManager;
import rustique.RustiqueParameters;

public class MainPane implements RustiquePane, RustiqueParameters {

    private static MainPane thisMainPane = null;
    private Pane thisPane;

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

        ImageView img0 = ImagesManager.getImageView(imagesPath + "setUpImage0.jpg");
        if(img0 != null) {
            img0.setLayoutY(0);
            img0.setLayoutX(hPadding);
        }

        ImageView img1 = ImagesManager.getImageView(imagesPath + "setUpImage1.jpg");
        if(img1 != null) {
            img1.setLayoutY(thisPane.getPrefHeight() - img1.getFitHeight());
            img1.setLayoutX(thisPane.getPrefWidth() - img1.getFitWidth());
        }

        ImageView img2 = ImagesManager.getImageView(imagesPath + "setUpImage2.jpg");
        if(img2 != null) {
            img2.setLayoutY(0);
            img2.setLayoutX(thisPane.getPrefWidth() - img2.getFitWidth());
        }

        ImageView img3 = ImagesManager.getImageView(imagesPath + "setUpImage3.jpg");
        if(img3 != null) {
            img3.setLayoutY(thisPane.getPrefHeight() - img3.getFitHeight());
            img3.setLayoutX(hPadding);
        }

        ImageView img4 = ImagesManager.getImageView(imagesPath + "mainImage.png");
        if(img4 != null) {
            img4.setLayoutY(screenHeight / 2 - img4.getFitHeight() / 2);
            img4.setLayoutX((thisPane.getPrefWidth() - img4.getFitWidth()) / 2);
        }

        thisPane.getChildren().addAll(img0, img1, img2, img3, img4);
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }
}
