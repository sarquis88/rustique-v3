package rustique.misc;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import rustique.bdd.RustiqueBDD;
import rustique.grids.MainGrid;
import rustique.panes.MainPane;
import rustique.panes.RustiquePane;

public class View implements RustiqueParameters {

    private static View thisView = null;

    private Scene thisScene;
    private Pane layout;
    private Pane actualPane = null;

    private Separator sep0;
    private Separator sep1;

    private int c;

    /**
     * Patron singleton
     * @return instancia unica de clase
     */
    public static View getInstance() {
        if(thisView == null)
            thisView = new View();
        return thisView;
    }

    /**
     * Constructor de clase
     */
    private View() {
        sep0 = new Separator();
        sep0.setOrientation(Orientation.VERTICAL);
        sep0.setLayoutX(sepLayoutX);
        sep0.setLayoutY(0);
        sep0.setPrefWidth(sepWidth);
        sep0.setPrefHeight(2000);
        sep0.setStyle(separatorStyle);

        sep1 = new Separator();
        sep1.setOrientation(Orientation.VERTICAL);
        sep1.setLayoutX(sepLayoutX * 2 + hPadding);
        sep1.setLayoutY(0);
        sep1.setPrefWidth(sepWidth);
        sep1.setPrefHeight(2000);
        sep1.setStyle(separatorStyle);

        MainGrid mainGrid = new MainGrid();
        mainGrid.setLayout( (sep0.getLayoutX() / 2) -
                (mainGrid.getGridPane().getPrefWidth() / 2), 0);
        mainGrid.setDisable("principal", true);

        layout = new Pane();
        layout.setStyle("-fx-background-color: gray;");
        layout.getChildren().addAll(mainGrid.getGridPane(), sep0, sep1);

        this.c = RustiqueBDD.getInstance().getColor();
        cambiarColor();

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

    /**
     * Retorno de posicion X de separadores
     * @param sep 0 para sep0, 1 para sep1
     * @return layoutX de separador
     */
    public double getSepLayoutX(int sep) {
        if(sep == 0)
            return sep0.getLayoutX();
        if(sep == 1)
            return sep1.getLayoutX();
        return 0;
    }

    public void cambiarColor() {
        switch (this.c) {
            case 0:
                layout.setStyle("-fx-background-color: red;");
                break;
            case 1:
                layout.setStyle("-fx-background-color: blue;");
                break;
            case 2:
                layout.setStyle("-fx-background-color: green;");
                break;
            case 3:
                layout.setStyle("-fx-background-color: yellow;");
                break;
            case 4:
                layout.setStyle("-fx-background-color: orange;");
                break;
            case 5:
                layout.setStyle("-fx-background-color: cyan;");
                break;
            case 6:
                layout.setStyle("-fx-background-color: brown;");
                break;
            case 7:
                layout.setStyle("-fx-background-color: gray;");
                break;
            default:
                MessagesManager.showFatalErrorAlert();
                break;
        }
        RustiqueBDD.getInstance().insertarColor(c);
        c++;
        if(c == 8)
            c = 0;
    }
}
