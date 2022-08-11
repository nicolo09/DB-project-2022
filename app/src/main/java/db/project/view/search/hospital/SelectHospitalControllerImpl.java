package db.project.view.search.hospital;

import java.util.function.Consumer;

import db.project.controller.Controller;
import db.project.model.Hospital;
import db.project.utils.Command;
import db.project.view.search.Selector;
import javafx.fxml.FXML;

public class SelectHospitalControllerImpl extends SearchHospitalControllerImpl{

    private final Command onSelect;

    public SelectHospitalControllerImpl(Command onExit, Command onSelect, Controller mainController, Selector selector, final Consumer<String> showError) {
        super(onExit, mainController, selector, h -> {
            showError.accept("Impossibile visualizzare attrezzature durante la selezione");
        }, h -> {
            showError.accept("Impossibile visualizzare stanze durante la selezione");
        }, showError);
        this.onSelect = onSelect;
    }

    @FXML
    private void onSelectButton() {
        onSelect.execute();
    }

    public Hospital getSelectedHospital() {
        return this.hospitalsTableView.getSelectionModel().getSelectedItem();
    }

}
