package db.project.view.search.referti;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.model.Report;
import db.project.view.search.SearchMainView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchRefertiControllerImpl {

    @FXML
    private ToggleGroup searchType;
    
    @FXML
    private Toggle toggleMedico;
    
    @FXML
    private Toggle togglePaziente;
    
    @FXML
    private TableView<Report> refertiTableView;
    
    @FXML
    private TableColumn<Report, Integer> codeColumn;
    
    @FXML
    private TableColumn<Report, Date> dateColumn;
    
    @FXML
    private TableColumn<Report, String> typeColumn;
    
    @FXML
    private TableColumn<Report, Integer> hospitalColumn;
    
    @FXML
    private TextField textCodiceFiscale;

    private final SearchMainView view;
    private final Controller controller;

    public SearchRefertiControllerImpl(final SearchMainView view, final Controller controller) {
        this.controller = controller;
        this.view = view;
    }

    @FXML
    void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("code"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Report, Date>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("type"));
        hospitalColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("hospital"));
    }

    @FXML
    private void onPersonSelectButton() {
        final Person selected = view.selectPerson();
        if (selected != null) {
            this.textCodiceFiscale.setText(selected.getCF());
        }
    }

    @FXML
    private void onAbortButton() {
        view.goToMainMenu();
    }

    @FXML
    private void onSearchButton() {
        Collection<Report> reports = this.getReports();
        if (reports.isEmpty()) {
            this.view.showError("Nessun referto trovato");
        }
        else {
            refertiTableView.getItems().setAll();            
        }
    }

    private Collection<Report> getReports() {
        if (textCodiceFiscale.getText().trim() != "") {
            if (searchType.getSelectedToggle().equals(toggleMedico)) {
                final Optional<Person> doc = controller.getDoctorByCF(this.textCodiceFiscale.getText());
                if (doc.isPresent()) {
                    return controller.getRefertiByDoctor(doc.get());
                } else {
                    view.showError("Medico non trovato");
                }
            } else if (searchType.getSelectedToggle().equals(togglePaziente)) {
                final Optional<Person> pat = controller.getPatientByCF(this.textCodiceFiscale.getText());
                if (pat.isPresent()) {
                    return controller.getRefertiByPatient(pat.get());
                } else {
                    view.showError("Paziente non trovato");
                }
            }
        } else {
            view.showError("Inserire un codice fiscale");
        }
        return List.of();
    }
}
