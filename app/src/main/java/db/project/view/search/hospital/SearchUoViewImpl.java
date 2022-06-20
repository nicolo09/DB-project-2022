package db.project.view.search.hospital;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchUoViewImpl implements SearchUoView {
    
    private static final String PATH = "search_uo.fxml";
    private final SearchUoControllerImpl controller;
    private final Stage mainStage;

    public SearchUoViewImpl(final Stage stage, final SearchUoControllerImpl controller) {
        this.mainStage = stage;
        this.controller = controller;
    }

    @Override
    public void show() {
        Parent parent = null;
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this.controller);
        loader.setLocation(getClass().getResource("/" + PATH));
        try {
            parent = loader.load();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final Scene scene = new Scene(parent);
        this.getStage().setScene(scene);
        this.getStage().show();
        this.getStage().setMinWidth(scene.getWidth());
        this.getStage().setMinHeight(scene.getHeight());
    }

    private Stage getStage() {
        return this.mainStage;
    }
}
