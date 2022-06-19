package db.project.view.search.hospital;

import java.util.Collection;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Uo;
import db.project.view.search.SearchMainView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SearchUoControllerImpl {

    private final Controller mainController;
    private final Command onExit;
    private final SearchMainView mainView;

    public SearchUoControllerImpl(final Command onExit, final Controller mainController, final SearchMainView mainView) {
        this.onExit = onExit;
        this.mainController = mainController;
        this.mainView = mainView;
    }

    @FXML
    private CheckBox checkCapacity;

    @FXML
    private CheckBox checkHospital;

    @FXML
    private CheckBox checkName;

    @FXML
    private TableView<Uo> uoTableView;

    @FXML
    private TableColumn<Uo, Integer> columnHospitalCode;

    @FXML
    private TableColumn<Uo, String> columnName;

    @FXML
    private TableColumn<Uo, Integer> columnOccupiedPlaces;

    @FXML
    private TableColumn<Uo, Integer> columnCapacity;

    @FXML
    private ToggleGroup toggleGroupCapacity;

    @FXML
    private TextField textHospitalCode;

    @FXML
    private TextField textName;

    @FXML
    private RadioButton toggleFree;

    @FXML
    private RadioButton toggleFull;

    @FXML
    private void initialize() {
        columnHospitalCode.setCellValueFactory(new Callback<CellDataFeatures<Uo, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(CellDataFeatures<Uo, Integer> p) {
                return new ReadOnlyObjectWrapper<>(p.getValue().getHospital().getCode());
            }
        });
        columnName.setCellValueFactory(new PropertyValueFactory<Uo, String>("name"));
        columnOccupiedPlaces.setCellValueFactory(new PropertyValueFactory<Uo, Integer>("occupiedPlaces"));
        columnCapacity.setCellValueFactory(new PropertyValueFactory<Uo, Integer>("capacity"));
    }

    @FXML
    void onAbortButton(ActionEvent event) {
        onExit.execute();
    }

    @FXML
    void onHospitalSelectButton(ActionEvent event) {
        this.textHospitalCode.setText(this.mainView.selectHospital().getCode().toString());
    }

    @FXML
    void onSearchButton(ActionEvent event) {
        uoTableView.getItems().clear();
        // More than one uo
        Optional<Boolean> freeSpaceOptional;
        if (checkCapacity.isSelected()) {
            RadioButton selected = (RadioButton) toggleGroupCapacity.getSelectedToggle();
            if (selected.equals(toggleFull)) {
                freeSpaceOptional = Optional.of(false);
            } else {
                freeSpaceOptional = Optional.of(true);
            }
        } else {
            freeSpaceOptional = Optional.empty();
        }
        final Collection<Uo> uos = mainController.getUos(
                checkName.isSelected() ? Optional.of(textName.getText()) : Optional.empty(), freeSpaceOptional,
                checkHospital.isSelected()
                        ? mainController.getHospital(Integer.parseInt(textHospitalCode.getText()))
                        : Optional.empty());
        uoTableView.getItems().addAll(uos);
    }

}
