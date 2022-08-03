package db.project.view.search.hospital;

import java.io.IOException;

import db.project.view.ViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchRoomViewImpl implements SearchRoomView {

    private static final String PATH = "search_room.fxml";
    private final Stage mainStage;
    private final SearchRoomControllerImpl controller;

    public SearchRoomViewImpl(final Stage stage, final SearchRoomControllerImpl controller) {
        this.mainStage = stage;
        this.controller = controller;
    }

    private Stage getStage(){
        return mainStage;
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

    @Override
    public void showAndWait() {
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
        this.getStage().showAndWait();
    }

}
