package rustique.panes;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import rustique.ImagesManager;
import rustique.RustiqueParameters;

public class ClientesPain implements RustiquePane, RustiqueParameters {

    private static ClientesPain thisClientesPain = null;
    private Pane thisPane;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static ClientesPain getInstance() {
        if (thisClientesPain == null)
            thisClientesPain = new ClientesPain();
        return thisClientesPain;
    }

    /**
     * Constructor de clase
     */
    private ClientesPain() {
        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);

        Label label = new Label("CLIENTES PRRITO");
        label.setLayoutX(thisPane.getPrefWidth() / 2);
        label.setLayoutY(thisPane.getPrefHeight() / 2);

        thisPane.getChildren().addAll(label);
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }
}