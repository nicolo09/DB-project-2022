package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RoomModifyController extends ModifyController{

	@FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtRoomNumber;


	public RoomModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var roomNumber = isInteger(txtRoomNumber.getText().trim()) ? Integer.parseInt(txtRoomNumber.getText().trim()) : null;
		
		showOutcome(this.mainController.insertRoom(hospitalCode, roomNumber));
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var roomNumber = isInteger(txtRoomNumber.getText().trim()) ? Integer.parseInt(txtRoomNumber.getText().trim()) : null;
		
		showOutcome(this.mainController.removeRoom(hospitalCode, roomNumber));
		
	}

	@FXML
    void selectElement() {
		//TODO
    }

    @FXML
    void selectHospital() {
    	//TODO
    }

}
