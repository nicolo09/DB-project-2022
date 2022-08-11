package db.project.view.search;

import db.project.controller.Controller;
import db.project.model.Cure;
import db.project.utils.Command;
import db.project.view.search.referti.SearchCureControllerImpl;
import javafx.fxml.FXML;

public class SelectCureControllerImpl extends SearchCureControllerImpl {

    private final Command onSelect;

    public SelectCureControllerImpl(Command onExit, Controller mainController, Selector selector) {
        super(onExit, mainController, selector);
        this.onSelect = onExit;
    }

    @FXML
    private void onSelect(){
        onSelect.execute();
    }

    public Cure getSelectedCure() {
        return cureTableView.getSelectionModel().getSelectedItem();
    }

}
