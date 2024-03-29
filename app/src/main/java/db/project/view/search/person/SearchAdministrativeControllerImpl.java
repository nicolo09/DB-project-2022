package db.project.view.search.person;

import java.util.Collection;
import java.util.Optional;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.utils.Command;

public class SearchAdministrativeControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Personale amministrativo";
    private final Controller mainController;

    public SearchAdministrativeControllerImpl(Command onExit, Controller mainController) {
        super(onExit, mainController);
        this.mainController = mainController;
    }

    @Override
    protected String getLabelText() {
        return LABEL;
    }

    @Override
    protected Collection<Person> getPersons() {
        return mainController.getAdministratives(
                this.nameCheckBox.isSelected() ? Optional.of(nameText.getText()) : Optional.empty(),
                this.surnameCheckBox.isSelected() ? Optional.of(surnameText.getText()) : Optional.empty(),
                Optional.empty(),Optional.empty());
    }
}
