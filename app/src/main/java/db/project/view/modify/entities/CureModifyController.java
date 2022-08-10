package db.project.view.modify.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.OPERATION_OUTCOME;
import db.project.model.PatientImpl;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
    
    private final Alert alert;

	public CureModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
		alert = new Alert(AlertType.WARNING, "", ButtonType.NO,  ButtonType.YES);
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
		
		var outcome = this.mainController.removeCure(patient, hospitalCode, operativeUnit, ingress);
		if(outcome.equals(OPERATION_OUTCOME.EXIT_DATE_MISSING)) {
			alert.setHeaderText("Data d'uscita mancante");
			alert.setContentText("Si sta cercando di rimuovere un ricovero ancora in corso." + NEWLINE +
					"Terminarlo ora e rimuoverlo?");
			alert.showAndWait().filter(btn -> btn.equals(ButtonType.YES)).ifPresent(a -> {
				var exit = Optional.of(Date.from(Instant.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()))));
				var second_outcome = this.mainController.updateCure(patient, hospitalCode, operativeUnit, ingress, exit, Optional.empty());
				if(second_outcome.equals(OPERATION_OUTCOME.SUCCESS)) {
					showOutcome(this.mainController.removeCure(patient, hospitalCode, operativeUnit, ingress));
				} else {
					showOutcome(second_outcome);
				}
			});
		} else {
			showOutcome(outcome);
		}			
		
		
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
		var cure = this.selector.selectCure();
		if(Objects.nonNull(cure)) {
			txtCF.setText(cure.getPatient().getCF());
			txtCodeHospital.setText(cure.getUo().getHospital().getCode().toString());
			txtNameUO.setText(cure.getUo().getName());
			txtMotivation.setText(cure.getReason());
			entryDate.setValue(cure.getDateIn());
			cure.getDateOut().ifPresent(date -> exitDate.setValue(date));
		}
    }

    @FXML
    private void selectPatientCF() {
    	var person = this.selector.selectPatient();
    	if(Objects.nonNull(person) && person instanceof PatientImpl) {
    		var patient = (PatientImpl) person;
    		
    		txtCF.setText(patient.getCF());
    	}
    }

    @FXML
    private void selectUO() {
    	var operative_unit = this.selector.selectUo();
    	if(Objects.nonNull(operative_unit)) {
    		txtNameUO.setText(operative_unit.getName());
    		txtCodeHospital.setText(operative_unit.getHospital().getCode().toString());
    	}
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCF,txtCodeHospital,txtMotivation,txtNameUO);
		this.clearDate(entryDate,exitDate);
	}

}
