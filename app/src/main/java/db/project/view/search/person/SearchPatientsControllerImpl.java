package db.project.view.search.person;

import db.project.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchPatientsControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Pazienti";
    
    public SearchPatientsControllerImpl(Command onExit) {
        super(onExit);
    }

    @Override
    @FXML
    protected void onSearch(ActionEvent event) {
        //TODO: Add patients only filter
    }

    @Override
    protected String getLabelText() {
        return LABEL;
    }
    
}
