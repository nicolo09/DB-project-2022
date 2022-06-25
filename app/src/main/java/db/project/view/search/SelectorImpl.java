package db.project.view.search;

import java.io.IOException;

import db.project.controller.Controller;
import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Uo;
import db.project.view.search.hospital.SelectASLControllerImpl;
import db.project.view.search.hospital.SelectHospitalControllerImpl;
import db.project.view.search.person.SelectPersonControllerImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * This class is used to select an entity using a dialog window.
 */
public class SelectorImpl implements Selector {

    private final Controller mainController;
    private final Stage mainStage;

    /**
     * 
     * @param mainController the application main controller.
     * @param mainStage the stage dialog window will be modal to.
     */
    public SelectorImpl(final Controller mainController, final Stage mainStage) {
        this.mainController = mainController;
        this.mainStage = mainStage;
    }

    @Override
    public Person selectPerson() {
        final Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectPersonControllerImpl controller = new SelectPersonControllerImpl(() -> stage.close(), () -> stage.close(),
                mainController);
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

    @Override
    public Hospital selectHospital() {
        final Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectHospitalControllerImpl controller = new SelectHospitalControllerImpl(() -> stage.close(),
                () -> stage.close(), mainController, this);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + "select_ospedali.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select hospital...");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
        return controller.getSelectedHospital();
    }

    @Override
    public ASL selectAsl() {
        final Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectASLControllerImpl controller = new SelectASLControllerImpl(() -> stage.close(), () -> stage.close(),
                mainController);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + "select_asl.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select ASL...");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
        return controller.getSelectedAsl();
    }

    @Override
    public Uo selectUo() {
        final Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectUoControllerImpl controller = new SelectUoControllerImpl(() -> stage.close(), () -> stage.close(),
                mainController, this);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + "select_uo.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select U.O. ...");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
        return controller.getSelectedUo();
    }

}
