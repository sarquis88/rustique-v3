package rustique.panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import rustique.controllers.ObrasController;
import rustique.misc.ImagesManager;
import rustique.misc.RustiqueParameters;
import rustique.misc.View;

import java.util.ArrayList;

public class DisplayPane extends RustiquePane implements RustiqueParameters {

    private static DisplayPane thisDisplayPane = null;
    private static ArrayList<String> fotos;

    private ImageView imageView;
    private int iterator = 0;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static DisplayPane getInstance() {
        if (thisDisplayPane == null)
            thisDisplayPane = new DisplayPane();
        else
            refreshFotos();
        return thisDisplayPane;
    }

    /**
     * Constructor de clase
     */
    private DisplayPane() {
        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);

        Label titulo = new Label("DISPLAY");
        titulo.setLayoutX(thisPane.getPrefWidth() / 2);
        titulo.setLayoutY(vPadding * 2);
        titulo.setStyle(tituloStyle);

        Button siguiente = new Button("->");
        siguiente.setPrefSize(buttonsWidth / 5, buttonsHeight);
        siguiente.setStyle(buttonsStyle);
        siguiente.setLayoutX(thisPane.getPrefWidth() - siguiente.getPrefWidth() - 2 * hPadding);
        siguiente.setLayoutY(thisPane.getPrefHeight() / 2);
        siguiente.setOnAction(e -> siguiente());
        siguiente.setTooltip(new Tooltip("Siguiente imagen"));

        Button anterior = new Button("<-");
        anterior.setPrefSize(buttonsWidth / 5, buttonsHeight);
        anterior.setStyle(buttonsStyle);
        anterior.setLayoutX(View.getInstance().getSepLayoutX(0) + 2 * hPadding);
        anterior.setLayoutY(thisPane.getPrefHeight() / 2);
        anterior.setOnAction(e -> anterior());
        anterior.setTooltip(new Tooltip("Anterior imagen"));

        refreshFotos();
        refreshImageView();
        thisPane.getChildren().addAll(titulo, anterior, siguiente);
    }

    /**
     * Ver imagen siguiente
     */
    private void siguiente() {
        iterator++;
        if(iterator >= fotos.size())
            iterator = 0;
        thisPane.getChildren().remove(imageView);
        refreshImageView();
    }

    /**
     * Ver imagen anterior
     */
    private void anterior() {
        iterator--;
        if(iterator < 0)
            iterator = fotos.size() - 1;
        thisPane.getChildren().remove(imageView);
        refreshImageView();
    }

    /**
     * Actualizacion de imageView
     */
    private void refreshImageView() {
        imageView = ImagesManager.getImageView(obrasPath + fotos.get(iterator) +
                "." + ImagesManager.getFormat(fotos.get(iterator)), true);
        if(imageView != null) {
            double midX = (thisPane.getPrefWidth() + View.getInstance().getSepLayoutX(0)) / 2;
            double midY = thisPane.getPrefHeight() / 2;
            imageView.setLayoutX(midX - imageView.getFitWidth() / 2);
            imageView.setLayoutY(midY - imageView.getFitHeight() / 2);
            Tooltip.install(imageView, new Tooltip(fotos.get(iterator)));
            thisPane.getChildren().addAll(imageView);
        }
    }

    /**
     * Actualizacion de obras con fotos
     */
    private static void refreshFotos() {
        fotos = ObrasController.getInstance().getObrasConFoto();
    }
}
