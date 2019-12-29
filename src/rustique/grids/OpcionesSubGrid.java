package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import rustique.controllers.ClientesController;
import rustique.controllers.ObrasController;
import rustique.controllers.TrabajosController;
import rustique.misc.RustiqueParameters;

public class OpcionesSubGrid extends RustiqueGrid implements RustiqueParameters {

    private static OpcionesSubGrid thisOpcionesSubGrid = null;

    private static Label[] cantidadesLabel;
    private static Label saldoTotalLabel;
    private static Label patrimonioLabel;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static OpcionesSubGrid getInstance() {
        if(thisOpcionesSubGrid == null)
            thisOpcionesSubGrid = new OpcionesSubGrid();
        return thisOpcionesSubGrid;
    }

    /**
     * Constructor de clase
     */
    private OpcionesSubGrid() {
        thisGrid = new GridPane();
        thisGrid.setHgap(10);
        thisGrid.setVgap(10);
        thisGrid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        String[] modelos = {"Clientes", "Obras", "Trabajos"};
        String[] cantidades = { String.valueOf(ClientesController.getInstance().getData().size()),
                                String.valueOf(ObrasController.getInstance().getData().size()),
                                String.valueOf(TrabajosController.getInstance().getData().size())};
        cantidadesLabel = new Label[modelos.length];

        for(int i=0; i<modelos.length; i++) {
            Label label = new Label((char) 126 + " Cantidad de " + modelos[i] + ":  ");
            label.setStyle(subSubTituloStyle);

            thisGrid.add(label, 0, i);
        }

        for(int i=0; i<cantidades.length; i++) {
            cantidadesLabel[i] = new Label(cantidades[i]);
            thisGrid.add(cantidadesLabel[i], 1, i);
        }

        saldoTotalLabel = new Label(String.valueOf(ClientesController.getInstance().getSaldoTotal()));
        Label label0 = new Label((char) 126 + " Saldo total:");
        label0.setStyle(subSubTituloStyle);

        patrimonioLabel = new Label(String.valueOf(ObrasController.getInstance().getPatrimonio()));
        Label label1 = new Label((char) 126 + " Patrimonio:");
        label1.setStyle(subSubTituloStyle);

        thisGrid.add(label0, 0, modelos.length + 3);
        thisGrid.add(label1, 0, modelos.length + 4);
        thisGrid.add(saldoTotalLabel, 1, modelos.length + 3);
        thisGrid.add(patrimonioLabel, 1, modelos.length + 4);
    }

    public static void refresh() {
        String[] cantidades = { String.valueOf(ClientesController.getInstance().getData().size()),
                String.valueOf(ObrasController.getInstance().getData().size()),
                String.valueOf(TrabajosController.getInstance().getData().size())};
        for(int i=0; i<cantidades.length; i++) {
            cantidadesLabel[i].setText(cantidades[i]);
        }

        saldoTotalLabel.setText(String.valueOf(ClientesController.getInstance().getSaldoTotal()));
        patrimonioLabel.setText(String.valueOf(ObrasController.getInstance().getPatrimonio()));
    }
}
