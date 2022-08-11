package db.project.view.search;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.utils.Command;
import db.project.view.search.person.SearchAdministrativeControllerImpl;
import javafx.fxml.FXML;

public class SelectAdministrativeControllerImpl extends SearchAdministrativeControllerImpl{

    private final Command onSelect;

    public SelectAdministrativeControllerImpl(Command onSelect, Controller mainController) {
        super(onSelect, mainController);
        this.onSelect = onSelect;
    }

    public Person getSelectedAdministrative() {
        return personTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onSelect() {
        onSelect.execute();
    }

}
