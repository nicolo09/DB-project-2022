package db.project.view.search.person;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.ASL;
import db.project.model.Person;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SearchPatientsControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Pazienti";
    private final Controller mainController;

    @FXML
    private CheckBox aslCheckbox;

    @FXML
    private TextField textAslCode;

    private Selector selector;

    public SearchPatientsControllerImpl(Command onExit, Controller mainController, Selector selector) {
        super(onExit, mainController);
        this.mainController = mainController;
        this.selector = selector;
    }

    @FXML
    private void onAslSelectButton() {
        final ASL selected = selector.selectAsl();
        if (selected != null) {
            this.textAslCode.setText(selected.getCode().toString());
        }
    }

    @Override
    protected String getLabelText() {
        return LABEL;
    }

    @Override
    protected Collection<Person> getPersons() {
        return mainController.getPatients(
                this.nameCheckBox.isSelected() ? Optional.of(nameText.getText()) : Optional.empty(),
                this.surnameCheckBox.isSelected() ? Optional.of(surnameText.getText()) : Optional.empty(),
                this.birthDateCheckbox.isSelected()
                        ? Optional.of(Date
                                .from(this.birthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        : Optional.empty(),
                this.aslCheckbox.isSelected() ? Optional.of(Integer.parseInt(textAslCode.getText()))
                        : Optional.empty());
    }

}
