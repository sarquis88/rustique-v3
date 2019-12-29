package rustique.panes;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rustique.grids.OpcionesSubGrid;
import rustique.misc.RustiqueParameters;
import rustique.misc.View;
import rustique.grids.OpcionesGrid;

public class OpcionesPane extends RustiquePane implements RustiqueParameters {

    private static OpcionesPane thisOpcionesPane = null;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static OpcionesPane getInstance() {
        if (thisOpcionesPane == null)
            thisOpcionesPane = new OpcionesPane();
        OpcionesSubGrid.refresh();
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
        OpcionesGrid.getInstance().setLayout(0, vPadding);

        Label estadisticas = new Label("Estadísticas");
        estadisticas.setLayoutX((thisPane.getPrefWidth() - View.getInstance().getSepLayoutX(0)) / 3);
        estadisticas.setLayoutY(titulo.getLayoutY() + vPadding * 20);
        estadisticas.setStyle(subTituloStyle);

        GridPane opcionesSubGrid = OpcionesSubGrid.getInstance().getGridPane();
        OpcionesSubGrid.getInstance().setLayout(estadisticas.getLayoutX() - hPadding * 5,
                estadisticas.getLayoutY() + vPadding * 10);

        thisPane.getChildren().addAll(gridPane, titulo, estadisticas, opcionesSubGrid);
    }
}
