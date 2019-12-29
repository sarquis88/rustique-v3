package rustique.panes;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rustique.misc.RustiqueParameters;
import rustique.misc.View;
import rustique.controllers.ClientesController;
import rustique.grids.ClientesGrid;
import rustique.models.Cliente;

public class ClientesPane extends RustiquePane implements RustiqueParameters {

    private static ClientesPane thisClientesPane = null;
    private static ClientesController thisController = null;

    private String clienteClickeado;
    private TableView<Cliente> tableView;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static ClientesPane getInstance() {
        if (thisClientesPane == null)
            thisClientesPane = new ClientesPane();
        return thisClientesPane;
    }

    /**
     * Constructor de clase
     */
    private ClientesPane() {
        thisController = ClientesController.getInstance();

        thisPane = new Pane();
        thisPane.setPrefSize(screenWidth - sepLayoutX, screenHeight);

        Label titulo = new Label("CLIENTES");
        titulo.setLayoutX(thisPane.getPrefWidth() / 2);
        titulo.setLayoutY(vPadding * 2);
        titulo.setStyle(tituloStyle);

        GridPane gridPane = ClientesGrid.getInstance().getGridPane();
        ClientesGrid.getInstance().setLayout(0, vPadding);

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

        TableColumn<Cliente, String> c0 = new TableColumn<>("Nombre");
        c0.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        c0.setPrefWidth( tableView.getPrefWidth()  * 0.6);
        c0.setStyle(tableColumnsStyle + "-fx-font-weight: bold;");
        TableColumn<Cliente, Integer> c1 = new TableColumn<>("Saldo");
        c1.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        c1.setPrefWidth( tableView.getPrefWidth() * 0.3);
        c1.setStyle(tableColumnsStyle);
        TableColumn<Cliente, Integer> c2 = new TableColumn<>("ID");
        c2.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setPrefWidth( tableView.getPrefWidth() * 0.1);
        c2.setStyle(tableColumnsStyle);

        tableView.setItems(thisController.getData());

        tableView.getColumns().add(c0);
        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);
        tableView.setOnMouseClicked(mouseEvent -> {
            // handler de clicks en tableView
            try {
                // localizacion de fila clickeada
                TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
                this.clienteClickeado = c0.getCellData(pos.getRow()); // nombre de fila clickeada
                ClientesGrid.getInstance().setDisable("deseleccionarCliente", false);
                if (mouseEvent.getClickCount() == 2) {
                    if (this.clienteClickeado != null)
                        thisController.actionPerformed("show-cliente");
                }
            }
            catch (IndexOutOfBoundsException e) {
                e.getMessage();
            }
        });

        scrollPane.setContent(tableView);

        thisPane.getChildren().addAll(titulo, gridPane, scrollPane);
    }

    /**
     * Getter del nombre del cliente que se clickeo en la tabla por ultima vez
     * @return nombre del cliente clickeado
     */
    public String getClienteClickeado() {
        return this.clienteClickeado;
    }

    /**
     * Setter del nombre del cliente que se clickeo en la tabla por ultima vez
     */
    public void setClienteClickeado(String clienteClickeado) {
        this.clienteClickeado = clienteClickeado;
    }

    public void resetClienteClickeado() {
        this.tableView.getSelectionModel().clearSelection();
        this.clienteClickeado = null;
        ClientesGrid.getInstance().setDisable("deseleccionarCliente", true);
    }
}