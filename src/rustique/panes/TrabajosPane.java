package rustique.panes;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rustique.controllers.TrabajosController;
import rustique.misc.RustiqueParameters;
import rustique.grids.TrabajosGrid;
import rustique.misc.View;
import rustique.models.Trabajo;

public class TrabajosPane implements RustiquePane, RustiqueParameters {

    private static TrabajosPane thisTrabajosPane = null;
    private static TrabajosController thisController = null;

    private Pane thisPane;
    private TableView<Trabajo> tableView;
    private String trabajoClickeado = null;

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

        thisController = TrabajosController.getInstance();

        Label titulo = new Label("TRABAJOS");
        titulo.setLayoutX(thisPane.getPrefWidth() / 2);
        titulo.setLayoutY(vPadding * 2);
        titulo.setStyle(tituloStyle);

        GridPane gridPane = TrabajosGrid.getInstance().getGridPane();
        TrabajosGrid.getInstance().setLayout(0, vPadding);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(View.getInstance().getSepLayoutX(0) + 3 * hPadding);
        scrollPane.setLayoutY(vPadding * 16);
        scrollPane.setPrefWidth(thisPane.getPrefWidth() - scrollPane.getLayoutX() - 3 * hPadding);
        scrollPane.setPrefHeight(thisPane.getPrefHeight() - 20 * vPadding);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setPrefWidth(scrollPane.getPrefWidth());
        tableView.setPrefHeight(scrollPane.getPrefHeight());

        TableColumn<Trabajo, String> c0 = new TableColumn<>("Cliente");
        c0.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        c0.setPrefWidth( tableView.getPrefWidth()  * 0.2);
        c0.setStyle(tableColumnsStyle + "-fx-font-weight: bold;");
        TableColumn<Trabajo, String> c1 = new TableColumn<>("Comentarios");
        c1.setCellValueFactory(new PropertyValueFactory<>("comentarios"));
        c1.setPrefWidth( tableView.getPrefWidth() * 0.6);
        c1.setStyle(tableColumnsStyle);
        TableColumn<Trabajo, String> c2 = new TableColumn<>("Fecha");
        c2.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        c2.setPrefWidth( tableView.getPrefWidth() * 0.1);
        c2.setStyle(tableColumnsStyle);
        TableColumn<Trabajo, Integer> c3 = new TableColumn<>("ID");
        c3.setCellValueFactory(new PropertyValueFactory<>("id"));
        c3.setPrefWidth( tableView.getPrefWidth() * 0.1);
        c3.setStyle(tableColumnsStyle);

        tableView.setItems(thisController.getData());

        tableView.getColumns().add(c0);
        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);
        tableView.getColumns().add(c3);
        tableView.setOnMouseClicked(mouseEvent -> {
            // handler de clicks en tableView
            try {
                // localizacion de fila clickeada
                TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
                this.trabajoClickeado = c0.getCellData(pos.getRow()); // nombre de fila clickeada
                TrabajosGrid.getInstance().setDisable("deseleccionarTrabajo", false);
                if (mouseEvent.getClickCount() == 2) {
                    if (this.trabajoClickeado != null)
                        thisController.actionPerformed("show-trabajo");
                }
            }
            catch (IndexOutOfBoundsException e) {
                e.getMessage();
            }
        });

        scrollPane.setContent(tableView);

        thisPane.getChildren().addAll(gridPane, titulo, scrollPane);
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }

    public String getTrabajoClickeado() {
        return this.trabajoClickeado;
    }

    public void resetTrabajoClickeado() {
        this.tableView.getSelectionModel().clearSelection();
        this.trabajoClickeado = null;
        TrabajosGrid.getInstance().setDisable("deseleccionarTrabajo", true);
    }

    public void setTrabajoClickeado(String nombre) {
        this.trabajoClickeado = nombre;
    }
}
