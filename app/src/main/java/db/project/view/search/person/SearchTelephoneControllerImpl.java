package db.project.view.search.person;

import db.project.controller.Controller;
import db.project.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Pair;

public class SearchTelephoneControllerImpl {

    private final Person person;
    private final Controller mainController;
    
    @FXML
    protected ListView<Pair<Person,String>> listTelephones;

    public SearchTelephoneControllerImpl(final Person person, final Controller mainController){
        this.person = person;
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        listTelephones.getItems().addAll(mainController.getTelephones(person));
        listTelephones.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Pair<Person, String> item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getValue());
                }
            }
        });
    }

}
