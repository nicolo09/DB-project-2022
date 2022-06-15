package db.project.view.search.person;

import db.project.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchPersonControllerImpl {

    private final Command onExit;
    
    public SearchPersonControllerImpl(final Command onExit) {
        this.onExit = onExit;
    }
    
    @FXML
    private CheckBox birthDateCheckbox;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private CheckBox nameCheckBox;

    @FXML
    private TextField nameText;

    @FXML
    private TableView<?> personTableView;

    @FXML
    private CheckBox surnameCheckBox;

    @FXML
    private TextField surnameText;

    @FXML
    void onBack(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    void onSearch(ActionEvent event) {
        //TODO: Search persons
    }

}
