package db.project.view.modify.entities;

import java.util.Objects;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RoomModifyController extends ModifyController{

	@FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtRoomNumber;


	public RoomModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
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
		var room = this.selector.selectRoom();
		if(Objects.nonNull(room)) {
			this.clearAll();
			txtRoomNumber.setText(room.getRoomNumber().toString());
			txtCodeHospital.setText(room.getHospital().getCode().toString());
		}
    }

    @FXML
    void selectHospital() {
    	var hospital = this.selector.selectHospital();
    	if(Objects.nonNull(hospital)) {
    		txtCodeHospital.setText(hospital.getCode().toString());
    	}
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCodeHospital,txtRoomNumber);
	}

}
