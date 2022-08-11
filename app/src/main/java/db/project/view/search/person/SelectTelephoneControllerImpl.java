package db.project.view.search.person;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.utils.Command;
import javafx.fxml.FXML;
import javafx.util.Pair;

public class SelectTelephoneControllerImpl extends SearchTelephoneControllerImpl {

    private final Command onSelect;

    public SelectTelephoneControllerImpl(final Person person, final Controller mainController, final Command onSelect) {
        super(person, mainController);
        this.onSelect = onSelect;
    }

    @FXML
    protected void onSelect() {
        onSelect.execute();
    }

    public Pair<Person,String> getSelectedTelephone() {
        return this.listTelephones.getSelectionModel().getSelectedItem();
    }

}
