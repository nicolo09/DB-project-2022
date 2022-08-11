package db.project.view.search.person;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.utils.Command;
import javafx.fxml.FXML;

public class SelectDoctorControllerImpl extends SearchDoctorControllerImpl {
    
    private final Command onSelect;
    
    public SelectDoctorControllerImpl(Command onExit, Controller mainController) {
        super(onExit, mainController);
        this.onSelect = onExit;
    }

    @FXML
    private void onSelect() {
        onSelect.execute();
    }

    public Person getSelectedDoctor() {
        return this.personTableView.getSelectionModel().getSelectedItem();
    }

}