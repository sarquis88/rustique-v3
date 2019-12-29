package rustique.misc;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import rustique.Main;
import rustique.bdd.RustiqueBDD;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImagesManager implements RustiqueParameters {

    /**
     * Permite buscar en nuestra memoria alguna imagen
     * @return imagen elegida
     */
    public static Image chooseImage() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        String path = RustiqueBDD.getInstance().getInitialDir();
        if(path == null) {
            MessagesManager.showFatalErrorAlert();
            return null;
        }
        else {
            fileChooser.setInitialDirectory(new File(path));
            try {
                return new Image(fileChooser.showOpenDialog(Main.getWindow()).toURI().toString());
            } catch (NullPointerException e) {
                return null;
            }
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
     * @param imgNombre nombre de imagen
     */
    public static void writeImage(Image img, String imgNombre) {
        // extraccion de formato (ultimas 3 letras)
        String format = img.getUrl().substring(img.getUrl().length() - 3);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), format,
                    new File(obrasPath + imgNombre + "." + format));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borrar imagen de memoria
     * @param imgNombre nombre de imagen a borrar
     */
    public static void removeImage(String imgNombre) {

        String format = getFormat(imgNombre);
        if(format == null) {
            MessagesManager.showFatalErrorAlert();
        }
        else {
            File file = new File(obrasPath + imgNombre + "." + format);
            if (!file.delete())
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
     * @param scale true para retornar imagen en escala "amigable"
     * @return objeto ImageView de dicha imagen
     */
    public static ImageView getImageView(String path, boolean scale) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        if(image != null) {
            if(!scale) {
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(image.getWidth());
                imageView.setFitHeight(image.getHeight());
                return imageView;
            }
            else
                return scale(image);
        }
        else
            return null;
    }

    public static void copiarBDD() {
        MessagesManager.showInformationAlert("Eligir carpeta destino para backup");

        DirectoryChooser directoryChooser = new DirectoryChooser();

        try {
            File dest = new File(directoryChooser.showDialog(Main.getWindow()).toURI());

            FileUtils.copyFileToDirectory(new File(bddPath), dest);
            FileUtils.copyDirectoryToDirectory(new File(obrasPath), dest);
        }
        catch (NullPointerException ignored) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cambiarImgNombre(String nombreViejo, String nombreNuevo) {

        String format = getFormat(nombreViejo);
        if(format == null) {
            MessagesManager.showFatalErrorAlert();
            return;
        }
        File nuevo = new File(obrasPath + nombreNuevo + "." + format);
        File viejo = new File(obrasPath + nombreViejo + "." + format);
        try {
            FileUtils.moveFile(viejo, nuevo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFormat(String nombre) {
        Image img = new Image("file:" + obrasPath + nombre + ".png");
        if(img.getHeight() != 0)
            return "png";

        img = new Image("file:" + obrasPath + nombre + ".jpg");
        if(img.getHeight() != 0)
            return "jpg";

        return null;
    }
}