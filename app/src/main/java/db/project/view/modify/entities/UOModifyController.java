package db.project.view.modify.entities;

import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
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

	public UOModifyController(Command exit, Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var capacity = isInteger(txtCapacity.getText().trim()) ? Integer.parseInt(txtCapacity.getText().trim()) : INVALID_INT;
		
		var seatsOccupied = isInteger(txtSeatsOccupied.getText().trim()) ? Integer.parseInt(txtSeatsOccupied.getText().trim()) : 0;
		
		showOutcome(this.mainController.insertUO(hospitalCode, name, capacity, seatsOccupied));
	}

	@Override
	@FXML
	protected void updateElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		Optional<Integer> capacity = isInteger(txtCapacity.getText().trim()) ? Optional.of(Integer.parseInt(txtCapacity.getText().trim())) : Optional.empty();
		
		showOutcome(this.mainController.updateUO(hospitalCode, name, capacity));
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		showOutcome(this.mainController.removeUO(hospitalCode, name));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCapacity, NUMBER_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtName, COMPLETE_FORMATTER);
		setTextFormatter(txtSeatsOccupied, NUMBER_FORMATTER);
	}
	
	@FXML
    private void selectElement() {
		var operative_unit = this.selector.selectUo();
		if(Objects.nonNull(operative_unit)) {
			txtCapacity.setText(operative_unit.getCapacity().toString());
			txtCodeHospital.setText(operative_unit.getHospital().getCode().toString());
			txtName.setText(operative_unit.getName());
			txtSeatsOccupied.setText(operative_unit.getOccupiedPlaces().toString());
		}
    }

    @FXML
    private void selectHospital() {
    	var hospital = this.selector.selectHospital();
    	if(Objects.nonNull(hospital)) {
    		txtCodeHospital.setText(hospital.getCode().toString());
    	}
    }

}
