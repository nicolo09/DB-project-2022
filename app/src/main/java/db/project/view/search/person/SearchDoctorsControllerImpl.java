package db.project.view.search.person;

import db.project.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchMedicsControllerImpl extends SearchPersonControllerImpl {

    public SearchMedicsControllerImpl(Command onExit) {
        super(onExit);
    }

    @Override
    @FXML
    void onSearch(ActionEvent event) {
        //TODO: Add medic only filter
    }
}
