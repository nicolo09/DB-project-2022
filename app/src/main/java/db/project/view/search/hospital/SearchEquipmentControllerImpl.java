package db.project.view.search.hospital;

import java.time.LocalDate;

import db.project.controller.Controller;
import db.project.model.Equipment;
import db.project.model.Hospital;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchEquipmentControllerImpl {

    private final Hospital hospital;
    private final Controller mainController;

    public SearchEquipmentControllerImpl(final Hospital hospital, final Controller mainController) {
        this.hospital = hospital;
        this.mainController = mainController;
    }

    @FXML
    private TableColumn<Equipment, Integer> columnCode;

    @FXML
    private TableColumn<Equipment, LocalDate> columnLastMaintenance;

    @FXML
    private TableColumn<Equipment, String> columnName;

    @FXML
    protected TableView<Equipment> tableViewEquipment;

    @FXML
    private void initialize() {
        columnCode.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("code"));
        columnLastMaintenance.setCellValueFactory(new PropertyValueFactory<Equipment, LocalDate>("lastMaintenance"));
        columnName.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
        tableViewEquipment.getItems().setAll(mainController.getEquipment(hospital));
    }
}
