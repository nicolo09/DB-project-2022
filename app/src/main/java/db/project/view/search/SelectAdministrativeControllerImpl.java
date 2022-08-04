package db.project.view.search;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;
import db.project.view.search.person.SearchAdministrativeControllerImpl;

public class SelectAdministrativeControllerImpl extends SearchAdministrativeControllerImpl{

    public SelectAdministrativeControllerImpl(Command onSelect, Controller mainController) {
        super(onSelect, mainController);
    }

    public Person getSelectedAdministrative() {
        return personTableView.getSelectionModel().getSelectedItem();
    }

}
