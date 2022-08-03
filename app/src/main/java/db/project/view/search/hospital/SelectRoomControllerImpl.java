package db.project.view.search.hospital;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Hospital;
import db.project.model.Room;
import javafx.fxml.FXML;

public class SelectRoomControllerImpl extends SearchRoomControllerImpl {

    private final Command onSelect;

    public SelectRoomControllerImpl(final Hospital hospital, final Controller mainController, final Command onExit) {
        super(hospital, mainController);
        this.onSelect = onExit;
    }

    @FXML
    private void onSelect() {
        onSelect.execute();
    }

    public Room getSelectedRoom() {
        return this.tableViewRooms.getSelectionModel().getSelectedItem();
    }
}