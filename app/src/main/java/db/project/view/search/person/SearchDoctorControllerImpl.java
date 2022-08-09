package db.project.view.search.person;

import java.util.Collection;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchDoctorControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Personale sanitario";
    private final Controller mainController;

    public SearchDoctorControllerImpl(Command onExit, Controller mainController) {
        super(onExit, mainController);
        this.mainController = mainController;
    }

    @FXML
    private CheckBox roleCheckbox;

    @FXML
    private TextField roleText;

    @FXML
    private TableColumn<Person, String> roleColumn;

    @FXML
    public void initialize() {
        super.initialize();
        roleColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("role"));
    }

    @Override
    protected String getLabelText() {
        return LABEL;
    }

    @Override
    protected Collection<Person> getPersons() {
        return mainController.getDoctors(
                this.nameCheckBox.isSelected() ? Optional.of(nameText.getText()) : Optional.empty(),
                this.surnameCheckBox.isSelected() ? Optional.of(surnameText.getText()) : Optional.empty(),
                this.roleCheckbox.isSelected() ? Optional.of(roleText.getText()) : Optional.empty()
                );
    }
}
