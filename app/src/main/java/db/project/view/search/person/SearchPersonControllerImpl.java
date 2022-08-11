package db.project.view.search.person;

import java.util.Collection;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.utils.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public abstract class SearchPersonControllerImpl{

    private final Command onExit;

    private final Controller mainController;

    public SearchPersonControllerImpl(final Command onExit, final Controller mainController) {
        this.onExit = onExit;
        this.mainController = mainController;
    }

    @FXML
    protected CheckBox birthDateCheckbox;

    @FXML
    protected DatePicker birthDatePicker;

    @FXML
    protected CheckBox nameCheckBox;

    @FXML
    protected TextField nameText;

    @FXML
    protected CheckBox surnameCheckBox;

    @FXML
    protected TextField surnameText;

    @FXML
    protected TableView<Person> personTableView;// = new TableView<Person>();

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> surnameColumn;

    @FXML
    private TableColumn<Person, String> CFColumn;

    @FXML
    private Label typeLabel;

    @FXML
    protected void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        CFColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("CF"));
        this.setLabelText(this.getLabelText());
    }

    public void setLabelText(String text) {
        typeLabel.setText(text);
    }

    protected abstract String getLabelText();

    @FXML
    void onMousePressed(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            if (personTableView.getSelectionModel().getSelectedItem() != null) {
                new SearchTelephoneViewImpl(personTableView.getSelectionModel().getSelectedItem(), mainController).show();
            }
        }
    }

    @FXML
    private void onBack(ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    protected void onSearch(ActionEvent event) {
        personTableView.getItems().setAll(getPersons());

    }

    protected abstract Collection<Person> getPersons();

}
