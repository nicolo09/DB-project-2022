package db.project.view.search.hospital;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.ASL;
import javafx.fxml.FXML;

public class SelectASLControllerImpl extends SearchASLControllerImpl {

    private final Command onSelect;

    public SelectASLControllerImpl(Command onExit, Command onSelect, Controller mainController) {
        super(onExit, mainController);
        this.onSelect = onSelect;
    }

    @FXML
    private void onSelectButton() {
        onSelect.execute();
    }

    public ASL getSelectedAsl() {
        return this.aslTableView.getSelectionModel().getSelectedItem();
    }

}
