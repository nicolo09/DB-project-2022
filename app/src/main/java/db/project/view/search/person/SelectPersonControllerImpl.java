package db.project.view.search.person;

import java.util.Collection;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;
import javafx.fxml.FXML;

public class SelectPersonControllerImpl extends SearchPersonControllerImpl {

    private final Controller mainController;
    private final Command onSelect;

    public SelectPersonControllerImpl(Command onExit, Command onSelect, Controller mainController) {
        super(onExit);
        this.mainController = mainController;
        this.onSelect = onSelect;
    }
    
    @FXML
    private void onSelect() {
        onSelect.execute();
    }

    public Person getSelectedPerson() {
        return this.personTableView.getSelectionModel().getSelectedItem();
    }

    @Override
    protected String getLabelText() {
        return "";
    }
    
    @Override
    protected Collection<Person> getPersons() {
        return mainController.getPersons(
                this.nameCheckBox.isSelected() ? Optional.of(nameText.getText()) : Optional.empty(),
                this.surnameCheckBox.isSelected() ? Optional.of(surnameText.getText()) : Optional.empty()
                );
    }

}