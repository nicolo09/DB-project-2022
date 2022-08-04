package db.project.view.search;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Appointment;
import db.project.view.search.referti.SearchAppointmentsControllerImpl;
import javafx.fxml.FXML;

public class SelectAppointmentControllerImpl extends SearchAppointmentsControllerImpl{

    private final Command onSelect;
    
    public SelectAppointmentControllerImpl(Command onExit, Controller mainController, Selector selector) {
        super(onExit, mainController, selector);
        this.onSelect = onExit;
    }

    @FXML
    private void onSelect(){
        onSelect.execute();
    }

    public Appointment getSelectedAppointment() {
        return tableViewAppointments.getSelectionModel().getSelectedItem();
    }

    
    

}
