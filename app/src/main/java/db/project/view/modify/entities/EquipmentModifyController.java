package db.project.view.modify.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.controller.Controller;
import db.project.utils.Command;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
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

	public EquipmentModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		Date lastMaintenance = !Objects.isNull(dpLastMaintenance.getValue()) 
				? Date.from(Instant.from(dpLastMaintenance.getValue().atStartOfDay(ZoneId.systemDefault()))) 
				: Date.from(Instant.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault())));
		
		showOutcome(this.mainController.insertEquipment(hospitalCode, name, lastMaintenance));
	}

	@Override
	@FXML
	protected void updateElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var inventoryCode = isInteger(txtCodInventory.getText().trim()) ? Integer.parseInt(txtCodInventory.getText().trim()) : INVALID_INT;
		
		Optional<Date> lastMaintenance = !Objects.isNull(dpLastMaintenance.getValue()) 
				? Optional.of(Date.from(Instant.from(dpLastMaintenance.getValue().atStartOfDay(ZoneId.systemDefault()))))
				: Optional.empty();

		showOutcome(this.mainController.updateEquipment(hospitalCode, inventoryCode, lastMaintenance));
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var inventoryCode = isInteger(txtCodInventory.getText().trim()) ? Integer.parseInt(txtCodInventory.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.removeEquipment(hospitalCode, inventoryCode));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCodInventory, NUMBER_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtName, SIMPLE_FORMATTER);
	}
	
	@FXML
    private void selectEquipment() {
		var equipment = this.selector.selectEquipment();
		if(Objects.nonNull(equipment)) {
			this.clearAll();
			txtName.setText(equipment.getName());
			txtCodeHospital.setText(equipment.getHospital().getCode().toString());
			txtCodInventory.setText(equipment.getCode().toString());
			dpLastMaintenance.setValue(equipment.getLastMaintenance());
		}
    }

    @FXML
    private void selectHospital() {
    	var hospital = this.selector.selectHospital();
    	if(Objects.nonNull(hospital)) {
    		txtCodeHospital.setText(hospital.getCode().toString());
    	}
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCodeHospital,txtCodInventory,txtName);
		this.clearDate(dpLastMaintenance);
	}

}
