package db.project.view.search.person;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.utils.Command;
import db.project.view.search.Selector;
import javafx.fxml.FXML;

public class SelectPatientsControllerImpl extends SearchPatientsControllerImpl {

    
    private final Command onSelect;
    
    public SelectPatientsControllerImpl(Command onExit, Controller mainController, Selector selector) {
        super(onExit, mainController, selector);
        this.onSelect = onExit;
    }

    @FXML
    private void onSelect() {
        onSelect.execute();
    }

    public Person getSelectedPatient() {
        return this.personTableView.getSelectionModel().getSelectedItem();
    }

}
