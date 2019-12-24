package rustique.panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rustique.RustiqueParameters;
import rustique.grids.ObrasGrid;
import rustique.grids.OpcionesGrid;

public class OpcionesPane implements RustiquePane, RustiqueParameters {

    private static OpcionesPane thisOpcionesPane = null;
    private Pane thisPane;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static OpcionesPane getInstance() {
        if (thisOpcionesPane == null)
            thisOpcionesPane = new OpcionesPane();
        return thisOpcionesPane;
    }

    /**
     * Constructor de clase
     */
    private OpcionesPane() {
        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);

        Label titulo = new Label("OPCIONES");
        titulo.setLayoutX(thisPane.getPrefWidth() / 2);
        titulo.setLayoutY(vPadding * 2);
        titulo.setStyle(tituloStyle);

        GridPane gridPane = OpcionesGrid.getInstance().getGridPane();
        ObrasGrid.getInstance().setLayout(0, vPadding);

        Button boton = new Button("TOCAME CAGÓN");
        boton.setLayoutX(thisPane.getPrefWidth() / 2);
        boton.setLayoutY(thisPane.getPrefHeight() / 2);
        boton.setOnAction(e -> botonAction());

        thisPane.getChildren().addAll(gridPane, titulo, boton);
    }

    private void botonAction() {
        Label label = new Label("MACHIRULO");
        label.setLayoutX(thisPane.getPrefWidth() * Math.random());
        label.setLayoutY(thisPane.getPrefHeight() * Math.random());

        thisPane.getChildren().add(label);
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }
}
