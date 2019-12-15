package rustique.panes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import rustique.RustiqueParameters;
import rustique.models.Cliente;
import rustique.models.Obra;

public class ObrasPane implements RustiqueParameters, RustiquePane {

    private static ObrasPane thisObrasPane = null;
    private Pane thisPane;

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

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(thisPane.getPrefWidth() * 0.75);
        scrollPane.setPrefHeight(thisPane.getPrefHeight() - 17 * vPadding);
        scrollPane.setLayoutX(thisPane.getPrefWidth() - scrollPane.getPrefWidth() - 2 * hPadding);
        scrollPane.setLayoutY(vPadding * 16);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        TableView<Obra> tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setPrefWidth(scrollPane.getPrefWidth());
        tableView.setPrefHeight(scrollPane.getPrefHeight());

        TableColumn<Obra, String> c0 = new TableColumn<>("Nombre");
        c0.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        c0.setPrefWidth( tableView.getPrefWidth()  * 0.6);
        TableColumn<Obra, Integer> c1 = new TableColumn<>("Saldo");
        c1.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        c1.setPrefWidth( tableView.getPrefWidth() * 0.3);
        TableColumn<Obra, Integer> c2 = new TableColumn<>("ID");
        c2.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setPrefWidth( tableView.getPrefWidth() * 0.1);

        ObservableList<Obra> data = FXCollections.observableArrayList();
        data.add(new Obra("Talampaya", 500, 1));
        tableView.setItems(data);

        tableView.getColumns().add(c0);
        tableView.getColumns().add(c1);
        tableView.getColumns().add(c2);

        scrollPane.setContent(tableView);

        thisPane.getChildren().addAll(titulo, scrollPane);
    }

    @Override
    public Pane getPane() {
        return thisPane;
    }
}
