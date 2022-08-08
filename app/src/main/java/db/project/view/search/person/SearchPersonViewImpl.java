package db.project.view.search.person;

import java.io.IOException;

import db.project.view.ViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchPersonViewImpl implements SearchPersonView{

    public static final String PATH = "search_persone.fxml";
    private final Stage stage; 
    private SearchPersonControllerImpl controller;
    
    public SearchPersonViewImpl(Stage stage, SearchPersonControllerImpl controller) {
        this.stage = stage;
        this.controller = controller;
    }
   
    private Stage getStage() {
        return this.stage;
    }

    protected String getPath() {
        return PATH;
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
        //this.controller.setLabelText(this.controller.getLabelText());
        final Scene scene = new Scene(parent);
        ViewImpl.adjustStageAndSetScene(getStage(), scene);
        this.getStage().show();
    }

}
