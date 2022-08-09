package db.project.view.search.referti;

import java.time.LocalDate;
import java.util.Optional;

import db.project.model.Cure;
import db.project.model.Person;
import db.project.model.Uo;
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
import db.project.Command;
import db.project.controller.Controller;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Pair;


public class SearchCureControllerImpl {

    private final Command onExit;
    private final Controller mainController;
    private final Selector selector;

    public SearchCureControllerImpl(final Command onExit, final Controller mainController, final Selector selector) {
        this.onExit = onExit;
        this.mainController = mainController;
        this.selector = selector;
    }

    private Uo selected;
    
    @FXML
    private CheckBox checkPaziente;

    @FXML
    private CheckBox checkUo;

    @FXML
    private CheckBox checkDate;
    
    @FXML
    protected TableView<Cure> cureTableView;

    @FXML
    private TableColumn<Cure, LocalDate> columnDataIngresso;

    @FXML
    private TableColumn<Cure, String> columnDataUscita;

    @FXML
    private TableColumn<Cure, String> columnMotivazione;

    @FXML
    private TableColumn<Cure, Integer> columnOspedale;

    @FXML
    private TableColumn<Cure, String> columnPaziente;

    @FXML
    private TableColumn<Cure, String> columnUo;
    
    @FXML
    private TextField textCodiceFiscale;

    @FXML
    private TextField textUo;

    @FXML
    private DatePicker dateInFrom;

    @FXML
    private DatePicker dateInTo;

    @FXML
    private void initialize(){
        columnPaziente.setCellValueFactory(new Callback<CellDataFeatures<Cure, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cure, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getPatient().getCF());
            }
        });
        columnOspedale.setCellValueFactory(new Callback<CellDataFeatures<Cure, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<Cure, Integer> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getUo().getHospital().getCode());
            }
        });
        columnUo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cure,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cure, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getUo().getName());
            }
        });
        columnDataIngresso.setCellValueFactory(new PropertyValueFactory<Cure, LocalDate>("dateIn"));
        columnDataUscita.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cure,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Cure, String> param) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getDateOut().map(LocalDate::toString).orElse("-"));
            }
        });
        columnMotivazione.setCellValueFactory(new PropertyValueFactory<Cure, String>("reason"));
    }

    @FXML
    void onAbortButton(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    void onPersonSelectButton(ActionEvent event) {
        final Person selected = this.selector.selectPatient();
        if (selected != null) {
            this.textCodiceFiscale.setText(selected.getCF());
        }
    }

    @FXML
    void onSearchButton(ActionEvent event) {
        this.cureTableView.getItems().setAll(this.mainController.getCures(
                this.checkPaziente.isSelected() ? mainController.getPatientByCF(this.textCodiceFiscale.getText()) : Optional.empty(),
                this.checkUo.isSelected() ? Optional.of(selected) : Optional.empty(),
                this.checkDate.isSelected() ? Optional.of(new Pair<>(this.dateInFrom.getValue(), this.dateInTo.getValue())) : Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
    }

    @FXML
    void onUoSelectButton(ActionEvent event) {
        selected = this.selector.selectUo();
        if (selected != null) {
            this.textUo.setText(selected.getHospital() + " - " + selected.getName());
        }
    }

}
