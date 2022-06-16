package db.project.view.search.person;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;

public class SearchPatientsControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Pazienti";
    private final Controller mainController;

    public SearchPatientsControllerImpl(Command onExit, Controller mainController) {
        super(onExit);
        this.mainController = mainController;
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
                Optional.empty());
    }

}
