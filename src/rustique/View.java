package rustique;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import rustique.panes.MainPane;
import rustique.panes.RustiquePane;

public class View implements RustiqueParameters {

    private static View thisView = null;
    private Controller thisController;
    private Scene thisScene;
    private Pane layout;
    private Pane actualPane = null;

    /**
     * Patron singleton
     * @param controller controlador de eventos
     * @return instancia unica de clase
     */
    public static View getInstance(Controller controller) {
        if(thisView == null)
            thisView = new View(controller);
        return thisView;
    }

    /**
     * Constructor de clase
     * @param controller de eventos
     */
    private View(Controller controller) {
        this.thisController = controller;

        Separator sep0 = new Separator();
        sep0.setOrientation(Orientation.VERTICAL);
        sep0.setLayoutX(sepLayoutX);
        sep0.setLayoutY(0);
        sep0.setPrefWidth(sepWidth);
        sep0.setPrefHeight(2000);
        sep0.setStyle("-fx-background-color: red;");

        Grid grid = new Grid(thisController);
        grid.setLayout( (sep0.getLayoutX() / 2) -
                (grid.getGridPane().getPrefWidth() / 2), 0);
        grid.setDisable("principal", true);

        layout = new Pane();
        layout.setStyle("-fx-background-color: gray;");
        layout.getChildren().addAll(grid.getGridPane(), sep0);

        changePane(MainPane.getInstance());

        this.thisScene = new Scene(layout);
    }

    public void changePane(RustiquePane newActualPane) {
        if(actualPane != null)
            this.layout.getChildren().remove(this.actualPane);

        this.actualPane = newActualPane.getPane();
        this.actualPane.setLayoutX(sepLayoutX + sepWidth);

        this.layout.getChildren().add(newActualPane.getPane());
    }

    /**
     * Devuelve ventana de vosta
     * @return objeto Scene
     */
    public Scene getScene() {
        return this.thisScene;
    }
}
