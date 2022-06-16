package db.project.view.search;

import java.io.IOException;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.view.View;
import db.project.view.search.person.SearchDoctorsControllerImpl;
import db.project.view.search.person.SearchManagersControllerImpl;
import db.project.view.search.person.SearchPatientsControllerImpl;
import db.project.view.search.person.SearchPersonView;
import db.project.view.search.person.SearchPersonViewImpl;
import db.project.view.search.person.SelectPersonControllerImpl;
import db.project.view.search.referti.SearchRefertiControllerImpl;
import db.project.view.search.referti.SearchRefertiView;
import db.project.view.search.referti.SearchRefertiViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchMainViewImpl implements SearchMainView {

    private static final String PATH = "search_selection.fxml";
    private final View mainView;
    private final Controller mainController;
    private final Stage mainStage;
    private Parent parent;
    
    public SearchMainViewImpl(View mainView, Controller mainController, Stage stage) {
        this.mainView = mainView;
        this.mainController = mainController;
        this.mainStage = stage;
        
    }

    @Override
    public void goToASL() {
        // TODO Auto-generated method stub

    }

    @Override
    public void goToAppuntamenti() {
        // TODO Auto-generated method stub

    }

    @Override
    public void goToOspedali() {
        // TODO Auto-generated method stub

    }

    @Override
    public void goToPazienti() {
        final SearchPersonView view = new SearchPersonViewImpl(mainStage, new SearchPatientsControllerImpl(() -> this.show(), mainController));
        view.show();
    }

    @Override
    public void goToPersonaleAmministrativo() {
        final SearchPersonView view = new SearchPersonViewImpl(mainStage, new SearchManagersControllerImpl(() -> this.show(), mainController));
        view.show();
    }

    @Override
    public void goToPersonaleSanitario() {
        final SearchPersonView view = new SearchPersonViewImpl(mainStage, new SearchDoctorsControllerImpl(() -> this.show(), mainController));
        view.show();
    }

    @Override
    public void goToReferti() {
        final SearchRefertiControllerImpl controller = new SearchRefertiControllerImpl(this, null);
        final SearchRefertiView view = new SearchRefertiViewImpl(controller, mainStage);
        view.show();
    }

    @Override
    public void goToRicoveri() {
        // TODO Auto-generated method stub

    }

    @Override
    public void goToUnitaOperative() {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        final FXMLLoader loader = new FXMLLoader();
        SearchMainControllerImpl controller = new SearchMainControllerImpl(this);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + PATH));
        try {
            this.parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Scene scene = new Scene(parent);
        this.mainStage.setScene(scene);
        this.mainStage.show();
        mainStage.setMinWidth(scene.getWidth());
        mainStage.setMinHeight(scene.getHeight());
    }

    @Override
    public void goToMainMenu() {
        this.mainView.goToMainMenu();
    }

    @Override
    public Person selectPerson() {
        Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectPersonControllerImpl controller = new SelectPersonControllerImpl(() -> stage.close(), () -> stage.close(), mainController);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + "select_persone.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select person...");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
        return controller.getSelectedPerson();
    }

}
