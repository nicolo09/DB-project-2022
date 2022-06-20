package db.project.view.search.referti;

import java.time.LocalDateTime;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Appointment;
import db.project.view.search.SearchMainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchAppointmentsControllerImpl {

    private final Command onExit;
    private final Controller mainController;
    private final SearchMainView mainView;

    public SearchAppointmentsControllerImpl(final Command onExit, final Controller mainController, final SearchMainView mainView) {
        this.onExit = onExit;
        this.mainController = mainController;
        this.mainView = mainView;
    }

    @FXML
    private CheckBox checkDate;

    @FXML
    private CheckBox checkDoctor;

    @FXML
    private CheckBox checkHospital;

    @FXML
    private CheckBox checkPatient;

    @FXML
    private TableView<Appointment> tableViewAppointments;

    @FXML
    private TableColumn<Appointment, LocalDateTime> columnDate;

    @FXML
    private TableColumn<Appointment, String> columnHospital;

    @FXML
    private TableColumn<Appointment, String> columnPatient;

    @FXML
    private TableColumn<Appointment, Integer> columnRoomNumber;

    @FXML
    private TableColumn<Appointment, String> columnType;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textDoctor;

    @FXML
    private TextField textHospitalCode;

    @FXML
    private TextField textPatient;

    @FXML
    void onAbortButton(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    void onDoctorSelectButton(ActionEvent event) {
        textDoctor.setText(this.mainView.selectPerson().getCF());
    }

    @FXML
    void onHospitalSelectButton(ActionEvent event) {
        textHospitalCode.setText(this.mainView.selectHospital().getCode().toString());
    }

    @FXML
    void onPatientSelectButton(ActionEvent event) {
        textPatient.setText(this.mainView.selectPerson().getCF());
    }

    @FXML
    void onSearchButton(ActionEvent event) {
        
    }

}
