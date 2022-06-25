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
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var roomNumber = isInteger(txtRoomNumber.getText().trim()) ? Integer.parseInt(txtRoomNumber.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.insertRoom(hospitalCode, roomNumber));
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var roomNumber = isInteger(txtRoomNumber.getText().trim()) ? Integer.parseInt(txtRoomNumber.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.removeRoom(hospitalCode, roomNumber));
		
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtRoomNumber, NUMBER_FORMATTER);
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
