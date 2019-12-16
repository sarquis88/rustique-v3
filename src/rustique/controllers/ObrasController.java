package rustique.controllers;

import javafx.collections.ObservableList;
import rustique.models.Obra;

public class ObrasController {

    private static ObrasController thisController = null;

    public static ObrasController getInstance() {
        if(thisController == null)
            thisController = new ObrasController();
        return thisController;
    }

    private ObrasController() {}

    public static ObservableList<Obra> getData() {
        return null;
    }

    public void actionPerformed(String action) {

    }
}
