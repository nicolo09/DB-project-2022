package db.project.view.search.referti;

import java.util.Date;
import db.project.controller.Controller;
import db.project.model.Person;
import db.project.model.Report;
import db.project.view.search.SearchMainView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SearchRefertiControllerImpl {

    @FXML
    private ToggleGroup searchType;
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
    private void onPersonSelectButton() {
        Person selected = view.selectPerson();
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
        
    }
}
