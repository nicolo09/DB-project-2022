package db.project.view.search.referti;

import java.time.LocalDateTime;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Appointment;
import db.project.view.search.Selector;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SearchAppointmentsControllerImpl {

    private final Command onExit;
    private final Controller mainController;
    private final Selector selector;

    public SearchAppointmentsControllerImpl(final Command onExit, final Controller mainController, final Selector selector) {
        this.onExit = onExit;
        this.mainController = mainController;
        this.selector = selector;
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
    protected TableView<Appointment> tableViewAppointments;

    @FXML
    private TableColumn<Appointment, String> columnHospital;

    @FXML
    private TableColumn<Appointment, Integer> columnRoomNumber;

    @FXML
    private TableColumn<Appointment, LocalDateTime> columnDate;
    
    @FXML
    private TableColumn<Appointment, String> columnType;
    
    @FXML
    private TableColumn<Appointment, String> columnPatient;    

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textDoctor;

    @FXML
    private TextField textHospitalCode;

    @FXML
    private TextField textPatient;

    @FXML
    void initialize() {
        columnHospital.setCellValueFactory(new Callback<CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Appointment, String> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getRoom().getHospital().getCode().toString());
            }
        });
        columnRoomNumber.setCellValueFactory(new Callback<CellDataFeatures<Appointment, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(CellDataFeatures<Appointment, Integer> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getRoom().getRoomNumber());
            }
        });
        columnDate.setCellValueFactory(new Callback<CellDataFeatures<Appointment, LocalDateTime>, ObservableValue<LocalDateTime>>() {
            public ObservableValue<LocalDateTime> call(CellDataFeatures<Appointment, LocalDateTime> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getDateTime());
            }
        });
        columnType.setCellValueFactory(new Callback<CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Appointment, String> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getType().toString());
            }
        });
        columnPatient.setCellValueFactory(new Callback<CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Appointment, String> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getPatient().getCF());
            }
        });
    }

    @FXML
    void onAbortButton(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    void onDoctorSelectButton(ActionEvent event) {
        textDoctor.setText(this.selector.selectPerson().getCF());
    }

    @FXML
    void onHospitalSelectButton(ActionEvent event) {
        textHospitalCode.setText(this.selector.selectHospital().getCode().toString());
    }

    @FXML
    void onPatientSelectButton(ActionEvent event) {
        textPatient.setText(this.selector.selectPerson().getCF());
    }

    @FXML
    void onSearchButton(ActionEvent event) {
        tableViewAppointments.getItems().clear();
        tableViewAppointments.getItems().setAll(mainController.getAppointments(
            checkDoctor.isSelected() ? mainController.getDoctorByCF(textDoctor.getText()) : Optional.empty(),
            checkPatient.isSelected() ? mainController.getPatientByCF(textPatient.getText()) : Optional.empty(),
            checkHospital.isSelected() ? mainController.getHospital(Integer.parseInt(textHospitalCode.getText())) : Optional.empty(),
            checkDate.isSelected() ? Optional.of(datePicker.getValue()) : Optional.empty())
        );
    }

}
