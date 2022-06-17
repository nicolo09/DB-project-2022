package db.project.view.search.referti;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchRefertiViewImpl implements SearchRefertiView {

    private final static String PATH = "search_referti.fxml";
    private final Stage stage;
    private Parent parent;
    
    public SearchRefertiViewImpl(final SearchRefertiControllerImpl controller, final Stage stage) {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + PATH));
        try {
            this.parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        final Scene scene = new Scene(parent);
        this.stage.setScene(scene);
        this.stage.show();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }
    
}
