package db.project.view.search.person;

import javafx.stage.Stage;

public class SearchDoctorsViewImpl extends SearchPersonViewImpl {

    private static String PATH = "search_dottori.fxml";

    public SearchDoctorsViewImpl(Stage stage, SearchPersonControllerImpl controller) {
        super(stage, controller);
    }

    @Override
    protected String getPath() {
        return PATH;
    }
    
}
