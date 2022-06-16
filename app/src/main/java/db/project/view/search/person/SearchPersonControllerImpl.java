package db.project.view.search.person;

import db.project.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public abstract class SearchPersonControllerImpl {

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
    private Label typeLabel;

    public void setLabelText(String text) {
        typeLabel.setText(text);
    }
    
    protected abstract String getLabelText();
    
    @FXML
    private void onBack(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    protected abstract void onSearch(ActionEvent event);

}
