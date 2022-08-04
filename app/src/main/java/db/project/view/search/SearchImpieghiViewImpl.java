package db.project.view.search;

import java.io.IOException;

import db.project.view.ViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchImpieghiViewImpl implements SearchImpieghiView{

    private final static String PATH = "search_impieghi.fxml";
    private final Stage stage;
    private Parent parent;
    
    public SearchImpieghiViewImpl(final Stage stage, final SearchImpieghiControllerImpl controller) {
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
        ViewImpl.adjustStageAndSetScene(stage, scene);
        this.stage.show();
    }

}
