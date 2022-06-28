package db.project.view.search.person;

import java.io.IOException;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.view.ViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchTelephoneViewImpl implements SearchTelephoneView{

    public static final String PATH = "search_telephones.fxml";
    private final Stage stage;
    private SearchTelephoneControllerImpl controller;

    public SearchTelephoneViewImpl(final Person person, final Controller mainController){
        this.stage = new Stage();
        this.controller = new SearchTelephoneControllerImpl(person, mainController);
    }

    @Override
    public void show() {
        Parent parent = null;
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this.controller);
        loader.setLocation(getClass().getResource("/" + this.getPath()));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Scene scene = new Scene(parent);
        ViewImpl.adjustStageAndSetScene(this.getStage(), scene);
        this.getStage().show();
    }

    private Stage getStage() {
        return this.stage;
    }

    private String getPath() {
        return PATH;
    }

}
