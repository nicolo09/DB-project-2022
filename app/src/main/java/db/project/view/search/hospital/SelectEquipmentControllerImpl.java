package db.project.view.search.hospital;

import db.project.controller.Controller;
import db.project.model.Equipment;
import db.project.model.Hospital;
import db.project.utils.Command;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class SelectEquipmentControllerImpl extends SearchEquipmentControllerImpl {
    
    private final Command onSelect;

    public SelectEquipmentControllerImpl(final Hospital hospital, final Controller controller, final Command onSelect) {
        super(hospital, controller);
        this.onSelect = onSelect;
    }

    public Equipment getSelectedEquipment() {
        return tableViewEquipment.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            if (tableViewEquipment.getSelectionModel().getSelectedItem() != null) {
                this.onSelect.execute();
            }
        }
    }

}
