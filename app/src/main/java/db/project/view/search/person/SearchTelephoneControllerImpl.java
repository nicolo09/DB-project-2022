package db.project.view.search.person;

import java.util.stream.Collectors;

import db.project.controller.Controller;
import db.project.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SearchTelephoneControllerImpl {

    private final Person person;
    private final Controller mainController;
    
    @FXML
    protected ListView<String> listTelephones;

    public SearchTelephoneControllerImpl(final Person person, final Controller mainController){
        this.person = person;
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        listTelephones.getItems().addAll(mainController.getTelephones(person).stream().map(a -> a.getValue()).collect(Collectors.toList()));
    }

}
