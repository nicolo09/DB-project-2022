package db.project.view.search.person;

import java.util.Collection;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Person;

public class SearchDoctorsControllerImpl extends SearchPersonControllerImpl {

    private static final String LABEL = "Personale sanitario";
    private final Controller mainController;

    public SearchDoctorsControllerImpl(Command onExit, Controller mainController) {
        super(onExit);
        this.mainController = mainController;
    }

    @Override
    @FXML
    protected void onSearch(ActionEvent event) {
        // TODO: Add medic only filter
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
                Optional.empty());
    }
}
