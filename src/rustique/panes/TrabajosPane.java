package rustique.panes;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rustique.RustiqueParameters;
import rustique.grids.TrabajosGrid;

public class TrabajosPane implements RustiquePane, RustiqueParameters {

    private static TrabajosPane thisTrabajosPane = null;
    private Pane thisPane;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static TrabajosPane getInstance() {
        if (thisTrabajosPane == null)
            thisTrabajosPane = new TrabajosPane();
        return thisTrabajosPane;
    }

    /**
     * Constructor de clase
     */
    private TrabajosPane() {
        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);

        Label titulo = new Label("TRABAJOS");
        titulo.setLayoutX(thisPane.getPrefWidth() / 2);
        titulo.setLayoutY(vPadding * 2);
        titulo.setStyle(tituloStyle);

        GridPane gridPane = TrabajosGrid.getInstance().getGridPane();
        TrabajosGrid.getInstance().setLayout(0, vPadding);

        thisPane.getChildren().addAll(gridPane, titulo);
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }
}
