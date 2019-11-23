package rustique;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImagesManager {

    /**
     * Retorna imagen localizada en path
     * @param path ubicacion de la imagen
     * @return objeto ImageView de dicha imagen
     */
    public static ImageView getImageView(String path) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        if(image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
            return imageView;
        }
        else
            return null;
    }

}