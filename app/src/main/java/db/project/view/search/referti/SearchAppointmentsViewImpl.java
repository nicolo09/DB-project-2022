package db.project.view.search.referti;

import java.io.IOException;

import db.project.view.ViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchAppointmentsViewImpl implements SearchAppointmentsView {
    private static final String PATH = "search_appuntamenti.fxml";
    private final SearchAppointmentsControllerImpl controller;
    private final Stage mainStage;

    public SearchAppointmentsViewImpl(final Stage stage, final SearchAppointmentsControllerImpl controller) {
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
        ViewImpl.adjustStageAndSetScene(getStage(), scene);
        this.getStage().show();
    }

    private Stage getStage() {
        return this.mainStage;
    }
}
