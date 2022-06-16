package db.project.view.search.person;

import db.project.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchDoctorsControllerImpl extends SearchPersonControllerImpl {
    
    private static final String LABEL = "Personale sanitario";

    public SearchDoctorsControllerImpl(Command onExit) {
        super(onExit);
    }

    @Override
    @FXML
    protected void onSearch(ActionEvent event) {
        //TODO: Add medic only filter
    }
    
    @Override
    protected String getLabelText() {
        return LABEL;
    }
}
