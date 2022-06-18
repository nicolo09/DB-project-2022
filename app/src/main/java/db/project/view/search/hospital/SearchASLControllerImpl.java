package db.project.view.search.hospital;

import java.util.Collection;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import db.project.model.ASL;

public class SearchASLControllerImpl {

    private final Controller mainController;
    private final Command onExit;

    public SearchASLControllerImpl(final Command onExit, final Controller mainController) {
        this.onExit = onExit;
        this.mainController = mainController;
    }

    @FXML
    private void initialize(){
        codeColumn.setCellValueFactory(new PropertyValueFactory<ASL, Integer>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ASL, String>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<ASL, String>("address"));
    }

    @FXML
    private TableView<ASL> aslTableView;
    @FXML
    private TableColumn<ASL, Integer> codeColumn;
    @FXML
    private TableColumn<ASL, String> nameColumn;
    @FXML
    private TableColumn<ASL, String> addressColumn;

    @FXML
    private TextField textName;

    @FXML
    void onAbortButton(final ActionEvent event) {
        this.onExit.execute();
    }

    @FXML
    void onSearchButton(final ActionEvent event) {
        final Collection<ASL> asls = mainController.getASL(Optional.of(textName.getText()), Optional.empty(), Optional.empty(), Optional.empty());
        aslTableView.getItems().addAll(asls);
    }

}
