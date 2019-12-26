package rustique.misc;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import rustique.Main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImagesManager {

    private static String obrasPath = "./src/images/obras/";

    /**
     * Permite buscar en nuestra memoria alguna imagen
     * @return imagen elegida
     */
    public static Image chooseImage() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory(new File(obrasPath));

        try {
            return new Image(fileChooser.showOpenDialog(Main.getWindow()).toURI().toString());
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Convierte una Image en una ImageView con un tamaño visible
     * y proporcionada
     * @param image Image a convertir
     * @return ImageView lista para mostrar
     */
    public static ImageView scale(Image image) {
        // valores usados para scaling
        double proporcion = image.getWidth() / image.getHeight();
        double prefWidth = 500;
        double maxHeight = 600;

        ImageView imageView = new ImageView(image);
        // comienzo scaling
        imageView.setFitWidth(prefWidth);
        imageView.setFitHeight(imageView.getFitWidth() / proporcion);
        if(imageView.getFitHeight() > maxHeight) {        // si es muy alta, recalculo
            imageView.setFitHeight(maxHeight);
            imageView.setFitWidth(maxHeight * proporcion);
        }
        // fin scaling
        return imageView;
    }

    /**
     * Escribe en memoria una imagen
     * @param img imagen a guardar
     * @param id id de imagen para usar en el nombre
     */
    public static void writeImage(Image img, int id) {
        // extraccion de formato (ultimas 3 letras)
        String format = img.getUrl().substring(img.getUrl().length() - 3);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), format,
                    new File(obrasPath + id + "." + format));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borrar imagen de memoria
     * @param id id de imagen a borrar
     */
    public static void removeImage(int id) {

        File file = new File(obrasPath + id + ".jpg");

        if(!file.delete()) {
            file = new File(obrasPath + id + ".png");
            if(!file.delete())
                MessagesManager.showFatalErrorAlert();
        }
    }

    /**
     * Borra todas las imagenes guardadas
     */
    public static void removeAllImages() {
        try {
            FileUtils.cleanDirectory(new File(obrasPath));
        } catch (IOException e) {
            e.getMessage();
            MessagesManager.showFatalErrorAlert();
        }
    }

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
            imageView.setFitWidth(image.getWidth());        // 695
            imageView.setFitHeight(image.getHeight());    // 400
            return imageView;
        }
        else
            return null;
    }

    public static String getObrasPath() {
        return obrasPath;
    }

    public static void copiarBDD() {
        MessagesManager.showErrorAlert("¡¡¡ NO IMPLEMENTADO !!!");
    }
}