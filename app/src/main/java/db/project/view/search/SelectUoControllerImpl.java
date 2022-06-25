package db.project.view.search;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Uo;
import db.project.view.search.hospital.SearchUoControllerImpl;
import javafx.fxml.FXML;

public class SelectUoControllerImpl extends SearchUoControllerImpl {

    private final Command onSelect;

    public SelectUoControllerImpl(final Command onExit, final Command onSelect, final Controller mainController, final Selector selector) {
        super(onExit, mainController, selector);
        this.onSelect = onSelect;
    }

    @FXML
    private void onSelectButton() {
        onSelect.execute();
    }

    public Uo getSelectedUo() {
        return this.uoTableView.getSelectionModel().getSelectedItem();
    }

}
