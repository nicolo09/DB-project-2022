package db.project.view.search.person;

import java.io.IOException;

import db.project.Command;
import db.project.view.search.SearchMainControllerImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchPersonViewImpl implements SearchPersonView{

    protected static final String PATH = "search_persone.fxml";
    protected final Stage stage;
    protected final Command onExit;

    public SearchPersonViewImpl(Stage stage, Command onExit) {
        this.stage = stage;
        this.onExit = onExit;
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
