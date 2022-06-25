package db.project.view.modify.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CureModifyController extends ModifyController{

	@FXML
    private DatePicker entryDate;

    @FXML
    private DatePicker exitDate;

    @FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextArea txtMotivation;

    @FXML
    private TextField txtNameUO;

	public CureModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var patient = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var operativeUnit = txtNameUO.getText().trim() != "" ? txtNameUO.getText().trim() : null;
		
		Date ingress = !Objects.isNull(entryDate.getValue()) ? Date.from(Instant.from(entryDate.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;
		
		Optional<Date> exit = !Objects.isNull(exitDate.getValue()) ? Optional.of(Date.from(Instant.from(exitDate.getValue().atStartOfDay(ZoneId.systemDefault())))) : Optional.empty();
		
		var description = txtMotivation.getText().trim();
		
		showOutcome(this.mainController.insertCure(patient, hospitalCode, operativeUnit, ingress, exit, description));
	}

	@Override
	@FXML
	protected void updateElement() {
		var patient = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var operativeUnit = txtNameUO.getText().trim() != "" ? txtNameUO.getText().trim() : null;
		
		Date ingress = !Objects.isNull(entryDate.getValue()) ? Date.from(Instant.from(entryDate.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;
		
		Optional<Date> exit = !Objects.isNull(exitDate.getValue()) ? Optional.of(Date.from(Instant.from(exitDate.getValue().atStartOfDay(ZoneId.systemDefault())))) : Optional.empty();
		
		Optional<String> description = txtMotivation.getText().trim() != "" ? Optional.of(txtMotivation.getText().trim()) : Optional.empty();
		
		showOutcome(this.mainController.updateCure(patient, hospitalCode, operativeUnit, ingress, exit, description));
	}

	@Override
	@FXML
	protected void removeElement() {
		var patient = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var operativeUnit = txtNameUO.getText().trim() != "" ? txtNameUO.getText().trim() : null;
		
		Date ingress = !Objects.isNull(entryDate.getValue()) ? Date.from(Instant.from(entryDate.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;
		
		showOutcome(this.mainController.removeCure(patient, hospitalCode, operativeUnit, ingress));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtMotivation, SIMPLE_FORMATTER);
		setTextFormatter(txtNameUO, COMPLETE_FORMATTER);
	}
	
	@FXML
    private void selectCure() {
		//TODO
    }

    @FXML
    private void selectPatientCF() {
    	//TODO
    }

    @FXML
    private void selectUO() {
    	//TODO
    }

}
