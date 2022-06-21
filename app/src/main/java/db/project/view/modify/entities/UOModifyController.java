package db.project.view.modify.entities;

import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UOModifyController extends ModifyController{

	@FXML
    private TextField txtCapacity;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSeatsOccupied;

	public UOModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var capacity = isInteger(txtCapacity.getText().trim()) ? Integer.parseInt(txtCapacity.getText().trim()) : null;
		
		var seatsOccupied = isInteger(txtSeatsOccupied.getText().trim()) ? Integer.parseInt(txtSeatsOccupied.getText().trim()) : 0;
		
		showOutcome(this.mainController.insertUO(hospitalCode, name, capacity, seatsOccupied));
	}

	@Override
	@FXML
	protected void updateElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		Optional<Integer> capacity = isInteger(txtCapacity.getText().trim()) ? Optional.of(Integer.parseInt(txtCapacity.getText().trim())) : Optional.empty();
		
		showOutcome(this.mainController.updateUO(hospitalCode, name, capacity));
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		showOutcome(this.mainController.removeUO(hospitalCode, name));
	}
	
	@FXML
    private void selectElement() {
		//TODO
    }

    @FXML
    private void selectHospital() {
    	//TODO
    }

}
