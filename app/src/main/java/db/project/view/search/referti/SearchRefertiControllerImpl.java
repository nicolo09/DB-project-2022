package db.project.view.search.referti;

import java.util.Date;
import java.util.Optional;

import db.project.controller.Controller;
import db.project.model.Doctor;
import db.project.model.Patient;
import db.project.model.Report;
import db.project.view.View;
import db.project.view.search.SearchMainView;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private final SearchMainView view;
    private final Controller controller;

    public SearchRefertiControllerImpl(final SearchMainView view, final Controller controller) {
        this.controller = controller;
        this.view = view;
    }

    @FXML
    private void onPersonSelectButton() {
        System.out.println("Selected button pressed");
    }

    @FXML
    private void onAbortButton() {
        view.goToMainMenu();
    }

    @FXML
    private void onSearchButton() {
        
    }
}
