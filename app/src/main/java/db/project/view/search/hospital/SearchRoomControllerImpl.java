package db.project.view.search.hospital;

import db.project.controller.Controller;
import db.project.model.Hospital;
import db.project.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchRoomControllerImpl {

    private final Hospital hospital;
    private final Controller mainController;

    public SearchRoomControllerImpl(final Hospital hospital, final Controller mainController) {
        this.hospital = hospital;
        this.mainController = mainController;
    }

    @FXML
    private TableColumn<Room, Integer> columnNumber;

    @FXML
    protected TableView<Room> tableViewRooms;

    @FXML
    private void initialize() {
        columnNumber.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomNumber"));
        tableViewRooms.getItems().setAll(mainController.getRooms(hospital));
    }
}