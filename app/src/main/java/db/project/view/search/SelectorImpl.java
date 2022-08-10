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
import db.project.view.search.hospital.SelectRoomControllerImpl;
import db.project.view.search.hospital.SelectUoControllerImpl;
import db.project.view.search.person.SelectDoctorControllerImpl;
import db.project.view.search.person.SelectPatientsControllerImpl;
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
        SelectPersonControllerImpl controller = new SelectPersonControllerImpl(() -> stage.close(), () -> stage.close(),
                mainController);
        this.showViewAndWait(stage, controller, "/select_persone.fxml", "Select person...");
        return controller.getSelectedPerson();
    }

    @Override
    public Hospital selectHospital() {
        final Stage stage = new Stage();
        SelectHospitalControllerImpl controller = new SelectHospitalControllerImpl(() -> stage.close(),
                () -> stage.close(), mainController, this, this::showError);
        this.showViewAndWait(stage, controller, "/select_ospedali.fxml", "Select hospital...");
        return controller.getSelectedHospital();
    }

    @Override
    public ASL selectAsl() {
        final Stage stage = new Stage();
        SelectASLControllerImpl controller = new SelectASLControllerImpl(() -> stage.close(), () -> stage.close(),
                mainController);
        this.showViewAndWait(stage, controller, "/select_asl.fxml", "Select ASL...");
        return controller.getSelectedAsl();
    }

    @Override
    public Uo selectUo() {
        final Stage stage = new Stage();
        SelectUoControllerImpl controller = new SelectUoControllerImpl(() -> stage.close(), () -> stage.close(),
                mainController, this);
        this.showViewAndWait(stage, controller, "/select_uo.fxml", "Select U.O. ...");
        return controller.getSelectedUo();
    }

    @Override
    public Equipment selectEquipment() {
        final Hospital selected = this.selectHospital();
        if (selected != null) {
            final Stage stage = new Stage();
            SelectEquipmentControllerImpl controller = new SelectEquipmentControllerImpl(selected, mainController,
                    () -> stage.close());
            this.showViewAndWait(stage, controller, "/select_equipment.fxml", "Select equipment...");
            return controller.getSelectedEquipment();
        }
        return null;
    }

    @Override
    public Person selectAdministrative() {
        final Stage stage = new Stage();
        SelectAdministrativeControllerImpl controller = new SelectAdministrativeControllerImpl(() -> stage.close(),
                mainController);
        this.showViewAndWait(stage, controller, "/select_persone.fxml", "Select administrative...");
        return controller.getSelectedAdministrative();
    }

    @Override
    public Person selectPatient() {
        final Stage stage = new Stage();
        SelectPatientsControllerImpl controller = new SelectPatientsControllerImpl(() -> stage.close(), mainController,
                this);
        this.showViewAndWait(stage, controller, "/select_pazienti.fxml", "Select patient...");
        return controller.getSelectedPatient();
    }

    @Override
    public Person selectDoctor() {
        final Stage stage = new Stage();
        SelectDoctorControllerImpl controller = new SelectDoctorControllerImpl(() -> stage.close(), mainController);
        this.showViewAndWait(stage, controller, "/select_dottori.fxml", "Select doctor...");
        return controller.getSelectedDoctor();
    }

    @Override
    public Room selectRoom(final Hospital selected) {
        if (selected != null) {
            final Stage stage = new Stage();
            SelectRoomControllerImpl controller = new SelectRoomControllerImpl(selected, mainController,
                    () -> stage.close());
            this.showViewAndWait(stage, controller, "/select_room.fxml", "Select room...");
            return controller.getSelectedRoom();
        }
        return null;
    }

    @Override
    public Room selectRoom() {
        final Hospital selected = this.selectHospital();
        return this.selectRoom(selected);
    }

    @Override
    public Appointment selectAppointment() {
        final Stage stage = new Stage();
        SelectAppointmentControllerImpl controller = new SelectAppointmentControllerImpl(() -> stage.close(),
                mainController, this);
        this.showViewAndWait(stage, controller, "/select_appuntamenti.fxml", "Select appointment...");
        return controller.getSelectedAppointment();
    }

    @Override
    public Cure selectCure() {
        final Stage stage = new Stage();
        SelectCureControllerImpl controller = new SelectCureControllerImpl(stage::close, mainController, this);
        this.showViewAndWait(stage, controller, "/select_cure.fxml", "Select cure...");
        return controller.getSelectedCure();
    }

    @Override
    public Report selectReport() {
        final Stage stage = new Stage();
        SelectReportsControllerImpl controller = new SelectReportsControllerImpl(mainController, stage::close, this,
                this::showError);
        this.showViewAndWait(stage, controller, "select_referti.fxml", "Select report...");
        return controller.getSelectedReport();
    }

    private void showError(String error) {
        Alert alert = new Alert(AlertType.ERROR, error);
        alert.showAndWait();
    }

    @Override
    public Pair<Person, String> selectPhone() {
        final Person selected = this.selectPerson();
        if (selected != null) {
            return this.selectPhone(selected);
        }
        else{
            return null;
        }
    }

    @Override
    public Pair<Person, String> selectPhone(final Person person) {
        final Stage stage = new Stage();
        SelectTelephoneControllerImpl controller = new SelectTelephoneControllerImpl(person, mainController,
                () -> stage.close());
        this.showViewAndWait(stage, controller, "select_telephones.fxml", "Select telephone ...");
        return controller.getSelectedTelephone();
    }

    @Override
    public Pair<Uo, Person> selectJob() {
        final Stage stage = new Stage();
        SelectJobControllerImpl controller = new SelectJobControllerImpl(stage::close, mainController, this,
                this::showError);
        this.showViewAndWait(stage, controller, "select_impieghi.fxml", "Select job...");
        return controller.getSelectedJob();
    }

    private void showViewAndWait(Stage stage, Object controller, String fxml, String title) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + fxml));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.showAndWait();
    }
}
