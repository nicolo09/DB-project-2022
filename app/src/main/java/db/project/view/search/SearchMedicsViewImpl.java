package db.project.view.search;

import java.io.IOException;

import db.project.Command;
import db.project.view.search.person.SearchDoctorsControllerImpl;
import db.project.view.search.person.SearchPersonControllerImpl;
import db.project.view.search.person.SearchPersonView;
import db.project.view.search.person.SearchPersonViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchMedicsViewImpl extends SearchPersonViewImpl implements SearchPersonView {

    protected static final String PATH = "search_medici.fxml";
    
    public SearchMedicsViewImpl(Stage stage, Command onExit) {
        super(stage, onExit);
    }
    
    @Override
    public void show() {
        Parent parent = null;
        final FXMLLoader loader = new FXMLLoader();
        final SearchPersonControllerImpl controller = new SearchDoctorsControllerImpl(onExit);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + PATH));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Scene scene = new Scene(parent);
        this.stage.setScene(scene);
        this.stage.show();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

}
