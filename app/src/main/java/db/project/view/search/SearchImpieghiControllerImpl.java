package db.project.view.search;

import java.util.Optional;
import java.util.function.Consumer;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;
import db.project.model.Uo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Pair;

public class SearchImpieghiControllerImpl {

    private final Controller mainController;
    private final Selector selector;
    private final Command onExit;

    public SearchImpieghiControllerImpl(final Command onExit, final Controller mainController, final Selector selector, final Consumer<String> errorReporter) {
        this.onExit = onExit;
        this.mainController = mainController;
        this.selector = selector;
        this.errorReporter = errorReporter;
    }

    @FXML
    protected TableView<Pair<Uo, Person>> impieghiTableView;

    @FXML
    private TableColumn<Pair<Uo, Person>, String> UOColumn;

    @FXML
    private TableColumn<Pair<Uo, Person>, String> doctorCF;

    @FXML
    private TableColumn<Pair<Uo, Person>, String> doctorNameColumn;

    @FXML
    private TableColumn<Pair<Uo, Person>, String> doctorSurnameColumn;

    @FXML
    private TableColumn<Pair<Uo, Person>, Integer> hospitalColumn;

    @FXML
    private ToggleGroup searchType;

    @FXML
    private TextField textCodiceFiscale;

    @FXML
    private TextField textUnita;

    @FXML
    private RadioButton toggleMedico;

    @FXML
    private RadioButton toggleUnita;
    private Uo selectedUO;
    private final Consumer<String> errorReporter;

    @FXML
    void onAbortButton(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    void onMedicoSelectButton(ActionEvent event) {
        final Person selected = this.selector.selectDoctor();
        if (selected != null) {
            this.textCodiceFiscale.setText(selected.getCF());
        }
    }

    @FXML
    void onSearchButton(ActionEvent event) {
        if(this.toggleMedico.isSelected()){
            this.impieghiTableView.getItems().clear();
            this.impieghiTableView.getItems().addAll(this.mainController.getImpieghi(this.mainController.getDoctorByCF(this.textCodiceFiscale.getText()), Optional.empty()));
        }
        else if(this.toggleUnita.isSelected()){
            if (this.selectedUO == null) {
                this.errorReporter.accept("Selezionare una UO");
                return;
            }
            else {
                this.impieghiTableView.getItems().clear();
                this.impieghiTableView.getItems().addAll(this.mainController.getImpieghi(Optional.empty(), Optional.of(this.selectedUO)));
            }
        }
    }

    @FXML
    void onUOSelectButton(ActionEvent event) {
        this.selectedUO = this.selector.selectUo();
        this.textUnita.setText(selectedUO.getName() + " " + selectedUO.getHospital().getName());
    }

}
