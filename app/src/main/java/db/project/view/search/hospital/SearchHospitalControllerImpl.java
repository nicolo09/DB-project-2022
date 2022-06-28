package db.project.view.search.hospital;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Hospital;
import db.project.view.search.Selector;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SearchHospitalControllerImpl {

    @FXML
    protected TableView<Hospital> hospitalsTableView;

    @FXML
    private TableColumn<Hospital, Integer> columnCode;

    @FXML
    private TableColumn<Hospital, String> columnName;

    @FXML
    private TableColumn<Hospital, String> columnAddress;

    @FXML
    private TableColumn<Hospital, Integer> columnAsl;

    @FXML
    private CheckBox checkAsl;

    @FXML
    private CheckBox checkName;

    @FXML
    private TextField textAslCode;

    @FXML
    private TextField textName;

    private final Controller mainController;
    private final Command onExit;
    private final Selector selector;
    private final Consumer<Hospital> onEquipment;

    public SearchHospitalControllerImpl(final Command onExit, final Controller mainController, final Selector selector, final Consumer<Hospital> onEquipment) {
        this.onExit = onExit;
        this.mainController = mainController;
        this.selector = selector;
        this.onEquipment = onEquipment;
    }

    @FXML
    void onEquipmentButton(ActionEvent event) {
        onEquipment.accept(hospitalsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void initialize() {
        columnCode.setCellValueFactory(new PropertyValueFactory<Hospital, Integer>("code"));
        columnName.setCellValueFactory(new PropertyValueFactory<Hospital, String>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Hospital, String>("address"));
        columnAsl.setCellValueFactory(new Callback<CellDataFeatures<Hospital, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(CellDataFeatures<Hospital, Integer> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getAsl().getCode());
            }
        });
    }

    @FXML
    private void onAbortButton(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    private void onAslSelectButton(ActionEvent event) {
        this.textAslCode.setText(this.selector.selectAsl().getCode().toString());
    }

    @FXML
    private void onSearchButton(ActionEvent event) {
        hospitalsTableView.getItems().clear();
        final Collection<Hospital> hospitals = mainController.getHospitals(
                checkName.isSelected() ? Optional.of(textName.getText()) : Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                checkAsl.isSelected() ? mainController.getASL(Integer.parseInt(textAslCode.getText()))
                        : Optional.empty());
        hospitalsTableView.getItems().addAll(hospitals);
    }

}
