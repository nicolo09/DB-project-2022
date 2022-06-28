package db.project.view.search;

import java.io.IOException;

import db.project.controller.Controller;
import db.project.model.ASL;
import db.project.model.Appointment;
import db.project.model.Cure;
import db.project.model.Equipment;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Report;
import db.project.model.Room;
import db.project.model.Uo;
import db.project.view.search.hospital.SelectASLControllerImpl;
import db.project.view.search.hospital.SelectEquipmentControllerImpl;
import db.project.view.search.hospital.SelectHospitalControllerImpl;
import db.project.view.search.person.SelectPersonControllerImpl;
import db.project.view.search.person.SelectTelephoneControllerImpl;
import db.project.view.search.referti.SelectReportsControllerImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * This class is used to select an entity using a dialog window.
 */
public class SelectorImpl implements Selector {

    private final Controller mainController;
    private final Stage mainStage;

    /**
     * 
     * @param mainController the application main controller.
     * @param mainStage      the stage dialog window will be modal to.
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

    @Override
    public Equipment selectEquipment() {
        final Hospital selected = this.selectHospital();
        if (selected != null) {
            final Stage stage = new Stage();
            final FXMLLoader loader = new FXMLLoader();
            SelectEquipmentControllerImpl controller = new SelectEquipmentControllerImpl(selected, mainController, () -> stage.close());
            loader.setController(controller);
            loader.setLocation(getClass().getResource("/" + "select_equipment.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Select equipment...");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);
            stage.showAndWait();
            return controller.getSelectedEquipment();
        }
        return null;
    }

    @Override
    public Person selectAdministrative() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person selectPatient() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person selectDoctor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Room selectRoom() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Appointment selectAppointment() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cure selectCure() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Report selectReport() {
        final Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectReportsControllerImpl controller = new SelectReportsControllerImpl(mainController, stage::close, this, this::showError);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + "select_referti.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select Report...");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
        return controller.getSelectedReport();
    }

    private void showError(String error){
        Alert alert = new Alert(AlertType.ERROR, error);
        alert.showAndWait();
    }

    @Override
    public Pair<Person, String> selectPhone() {
        final Person selected = this.selectPerson();
        if (selected != null) {
            return this.selectPhone(selected);
        }
        return null;
    }

    @Override
    public Pair<Person, String> selectPhone(final Person person) {
        final Stage stage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        SelectTelephoneControllerImpl controller = new SelectTelephoneControllerImpl(person, mainController,
                () -> stage.close());
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + "select_telephones.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select telephone ...");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
        return controller.getSelectedTelephone();
    }

    @Override
    public Pair<Uo, Person> selectJob() {
        // TODO Auto-generated method stub
        return null;
    }

}
