package db.project.view.search.hospital;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Hospital;
import db.project.view.search.SearchMainView;
import javafx.fxml.FXML;

public class SelectHospitalControllerImpl extends SearchHospitalControllerImpl{

    private final Command onSelect;

    public SelectHospitalControllerImpl(Command onExit, Command onSelect, Controller mainController, SearchMainView mainView) {
        super(onExit, mainController, mainView);
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
