package rustique.panes;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rustique.RustiqueParameters;
import rustique.View;
import rustique.controllers.ObrasController;
import rustique.grids.ObrasGrid;
import rustique.models.Obra;

public class ObrasPane implements RustiqueParameters, RustiquePane {

    private static ObrasPane thisObrasPane = null;
    private Pane thisPane;

    private String obraClickeada;
    private TableView<Obra> tableView;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static ObrasPane getInstance() {
        if (thisObrasPane == null)
            thisObrasPane = new ObrasPane();
        return thisObrasPane;
    }

    /**
     * Constructor de clase
     */
    private ObrasPane() {
        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);

        Label titulo = new Label("OBRAS");
        titulo.setLayoutX(thisPane.getPrefWidth() / 2);
        titulo.setLayoutY(vPadding * 2);
        titulo.setStyle(tituloStyle);

        GridPane gridPane = ObrasGrid.getInstance().getGridPane();
        ObrasGrid.getInstance().setLayout(0, vPadding);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(View.getInstance().getSepLayoutX(0) + 3 * hPadding);
        scrollPane.setLayoutY(vPadding * 16);
        scrollPane.setPrefWidth(thisPane.getPrefWidth() - scrollPane.getLayoutX() - 3 * hPadding);
        scrollPane.setPrefHeight(thisPane.getPrefHeight() - 17 * vPadding);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.tableView = new TableView<>();
        this.tableView.setEditable(false);
        this.tableView.setPrefWidth(scrollPane.getPrefWidth());
        this.tableView.setPrefHeight(scrollPane.getPrefHeight());

        TableColumn<Obra, String> c0 = new TableColumn<>("Nombre");
        c0.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        c0.setPrefWidth( this.tableView.getPrefWidth()  * 0.23);
        c0.setStyle(tableColumnsStyle + "-fx-font-weight: bold;");
        TableColumn<Obra, String> c1 = new TableColumn<>("Autor");
        c1.setCellValueFactory(new PropertyValueFactory<>("autor"));
        c1.setPrefWidth( this.tableView.getPrefWidth() * 0.23);
        c1.setStyle(tableColumnsStyle);
        TableColumn<Obra, Integer> c2 = new TableColumn<>("Precio");
        c2.setCellValueFactory(new PropertyValueFactory<>("precio"));
        c2.setPrefWidth( this.tableView.getPrefWidth()  * 0.12);
        c2.setStyle(tableColumnsStyle);
        TableColumn<Obra, String> c3 = new TableColumn<>("Tipo");
        c3.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        c3.setPrefWidth( this.tableView.getPrefWidth() * 0.13);
        c3.setStyle(tableColumnsStyle);
        TableColumn<Obra, String> c4 = new TableColumn<>("Tamaño");
        c4.setCellValueFactory(new PropertyValueFactory<>("tamanio"));
        c4.setPrefWidth( this.tableView.getPrefWidth() * 0.13);
        c4.setStyle(tableColumnsStyle);
        TableColumn<Obra, Integer> c5 = new TableColumn<>("ID");
        c5.setCellValueFactory(new PropertyValueFactory<>("id"));
        c5.setPrefWidth( this.tableView.getPrefWidth() * 0.07);
        c5.setStyle(tableColumnsStyle);
        TableColumn<Obra, String> c6 = new TableColumn<>("Imagen");
        c6.setCellValueFactory(new PropertyValueFactory<>("hasImage"));
        c6.setPrefWidth( this.tableView.getPrefWidth() * 0.09);
        c6.setStyle(tableColumnsStyle);

        this.tableView.setItems(ObrasController.getInstance().getData());
        this.tableView.getColumns().add(c0);
        this.tableView.getColumns().add(c1);
        this.tableView.getColumns().add(c2);
        this.tableView.getColumns().add(c3);
        this.tableView.getColumns().add(c4);
        this.tableView.getColumns().add(c5);
        this.tableView.getColumns().add(c6);
        this.tableView.setOnMouseClicked(mouseEvent -> {
            // handler de clicks en tableView
            try {
                // localizacion de fila clickeada
                TablePosition pos = this.tableView.getSelectionModel().getSelectedCells().get(0);
                this.obraClickeada = c0.getCellData(pos.getRow()); // nombre de fila clickeada
                if (mouseEvent.getClickCount() == 2) {
                    if (this.obraClickeada != null)
                        ObrasController.getInstance().actionPerformed("show-obra");
                }
            }
            catch (IndexOutOfBoundsException e) {
                e.getMessage();
            }
        });

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(this.tableView);

        thisPane.getChildren().addAll(titulo, gridPane, scrollPane);
    }

    public String getObraClickeada() {
        return this.obraClickeada;
    }

    public void setObraClickeada(String obraClickeada) {
        this.obraClickeada = obraClickeada;
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }

    public void resetObraClickeada() {
        this.tableView.getSelectionModel().clearSelection();
        this.obraClickeada = null;
    }
}
