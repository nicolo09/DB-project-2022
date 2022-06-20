package db.project.view.modify.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EquipmentModifyController extends ModifyController{

	@FXML
    private DatePicker dpLastMaintenance;

    @FXML
    private TextField txtCodInventory;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtName;

	public EquipmentModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		Date lastMaintenance = !Objects.isNull(dpLastMaintenance.getValue()) 
				? Date.from(Instant.from(dpLastMaintenance.getValue().atStartOfDay(ZoneId.systemDefault()))) 
				: Date.from(Instant.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault())));
		
		this.mainController.insertEquipment(hospitalCode, name, lastMaintenance);
	}

	@Override
	@FXML
	protected void updateElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var inventoryCode = isInteger(txtCodInventory.getText().trim()) ? Integer.parseInt(txtCodInventory.getText().trim()) : null;
		
		Optional<Date> lastMaintenance = !Objects.isNull(dpLastMaintenance.getValue()) 
				? Optional.of(Date.from(Instant.from(dpLastMaintenance.getValue().atStartOfDay(ZoneId.systemDefault()))))
				: Optional.empty();

		this.mainController.updateEquipment(hospitalCode, inventoryCode, lastMaintenance);
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		var inventoryCode = isInteger(txtCodInventory.getText().trim()) ? Integer.parseInt(txtCodInventory.getText().trim()) : null;
		
		this.mainController.removeEquipment(hospitalCode, inventoryCode);
	}
	
	@FXML
    private void selectEquipment() {
		//TODO
    }

    @FXML
    private void selectHospital() {
    	//TODO
    }

}
