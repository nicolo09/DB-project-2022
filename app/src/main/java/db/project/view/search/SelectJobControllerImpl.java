package db.project.view.search;

import java.util.function.Consumer;

import db.project.controller.Controller;
import db.project.model.Person;
import db.project.model.Uo;
import db.project.utils.Command;
import javafx.fxml.FXML;
import javafx.util.Pair;

public class SelectJobControllerImpl extends SearchImpieghiControllerImpl {

    private final Command onSelect;

    public SelectJobControllerImpl(Command onExit, Controller mainController, Selector selector,
            Consumer<String> errorReporter) {
        super(onExit, mainController, selector, errorReporter);
        this.onSelect = onExit;
    }

    public Pair<Uo, Person> getSelectedJob() {
        return this.impieghiTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onSelect(){
        this.onSelect.execute();
    }

}
