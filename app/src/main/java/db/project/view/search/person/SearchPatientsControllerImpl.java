package db.project.view.search.person;

import db.project.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchPatientsControllerImpl extends SearchPersonControllerImpl {

    public SearchPatientsControllerImpl(Command onExit) {
        super(onExit);
    }

    @Override
    @FXML
    void onSearch(ActionEvent event) {
        //TODO: Add patients only filter
    }
}
