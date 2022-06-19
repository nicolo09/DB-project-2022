package db.project.view.search.person;

import javafx.stage.Stage;

public class SearchPatientsViewImpl extends SearchPersonViewImpl {
    
    private static final String PATH = "search_pazienti.fxml";

    public SearchPatientsViewImpl(final Stage stage, final SearchPersonControllerImpl controller) {
        super(stage, controller);
    }

    protected String getPath() {
        return PATH;
    }

    
}
