package db.project.view.search.person;

import java.util.Collection;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;

public class SearchManagersControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Personale amministrativo";
    private final Controller mainController;

    public SearchManagersControllerImpl(Command onExit, Controller mainController) {
        super(onExit);
        this.mainController = mainController;
    }

    @Override
    protected String getLabelText() {
        return LABEL;
    }

    @Override
    protected Collection<Person> getPersons() {
        return mainController.getManagers(
                this.nameCheckBox.isSelected() ? Optional.of(nameText.getText()) : Optional.empty(),
                this.surnameCheckBox.isSelected() ? Optional.of(surnameText.getText()) : Optional.empty(),
                Optional.empty(),Optional.empty());
    }
}
