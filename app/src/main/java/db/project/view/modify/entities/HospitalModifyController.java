package db.project.view.modify.entities;

import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.OPERATION_OUTCOME;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class HospitalModifyController extends ModifyController{
	
	@FXML
    private TextField txtASLCode;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtFacilityCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStreet;

    @FXML
    private TextField txtStreetNumber;
    
    private final Alert alert;

	public HospitalModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
		alert = new Alert(AlertType.WARNING, "", ButtonType.NO,  ButtonType.YES);
	}

	@Override
	@FXML
	protected void addElement() {
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var city = txtCity.getText().trim() != "" ? txtCity.getText().trim() : null;
		
		var street = txtStreet.getText().trim() != "" ? txtStreet.getText().trim() : null;
		
		var streetNumber = isInteger(txtStreetNumber.getText().trim()) ? Integer.parseInt(txtStreetNumber.getText().trim()) : INVALID_INT;
		
		var aslCode = isInteger(txtASLCode.getText().trim()) ? Integer.parseInt(txtASLCode.getText().trim()) : INVALID_INT; 
		
		showOutcome(this.mainController.insertHospital(name, city, street, streetNumber, aslCode));
	}

	@Override
	@FXML
	protected void updateElement() {
		var structureCode = isInteger(txtFacilityCode.getText().trim()) ? Integer.parseInt(txtFacilityCode.getText().trim()) : INVALID_INT;
		
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		
		Optional<Integer> aslCode = isInteger(txtASLCode.getText().trim()) ? Optional.of(Integer.parseInt(txtASLCode.getText().trim())) : Optional.empty(); 
		
		showOutcome(this.mainController.updateHospital(structureCode, name, aslCode));
	}

	@Override
	@FXML
	protected void removeElement() {
		var structureCode = isInteger(txtFacilityCode.getText().trim()) ? Integer.parseInt(txtFacilityCode.getText().trim()) : INVALID_INT;
		
		this.mainController.setHospital(structureCode);
		var message = setAlertMessage();
		if(message.contains("-1")) {
			showOutcome(OPERATION_OUTCOME.FAILURE);
		} else {
			alert.setHeaderText("Rimozione ospedale, procedere?");
			alert.setContentText(message);
			alert.showAndWait().filter(btn -> btn.equals(ButtonType.YES)).ifPresent(a -> {
				showOutcome(this.mainController.removeHospital(structureCode));
			});
		}
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtASLCode, NUMBER_FORMATTER);
		setTextFormatter(txtFacilityCode, NUMBER_FORMATTER);
		setTextFormatter(txtCity, SIMPLE_FORMATTER);
		setTextFormatter(txtStreet, SIMPLE_FORMATTER);
		setTextFormatter(txtStreetNumber, NUMBER_FORMATTER);
		setTextFormatter(txtName, COMPLETE_FORMATTER);
	}
	
	@FXML
    private void selectElement() {
		var hospital = this.selector.selectHospital();
		if(Objects.nonNull(hospital)) {
			txtFacilityCode.setText(hospital.getCode().toString());
			txtName.setText(hospital.getName());
			txtCity.setText(hospital.getAddressCity());
			txtStreet.setText(hospital.getAddressWay());
			txtStreetNumber.setText(hospital.getAddressNumber());
			txtASLCode.setText(hospital.getAsl().getCode().toString());
		}
    }
	
	@FXML
	private void selectASL() {
		var asl = this.selector.selectAsl();
		if(Objects.nonNull(asl)) {
			txtASLCode.setText(asl.getCode().toString());
		}
	}
	
	
	
	private String setAlertMessage() {
		return "La rimozione di questo ospedale comporterà anche la rimozione di: " + NEWLINE
				+ "Attrezzature: " + this.mainController.countDeletedEquipments() + NEWLINE
				+ "Amministrativi: " + this.mainController.countDeletedAmministratives() + NEWLINE
				+ "Stanze: " + this.mainController.countDeletedRooms() + NEWLINE
				+ "Unità operative: " + this.mainController.countDeletedUOs() + NEWLINE
				+ "Cure registrate: " + this.mainController.countDeletedCures() + NEWLINE
				+ "Personale Sanitario registrato: " + this.mainController.countDeletedJobs() + NEWLINE
				+ "Appuntamenti prenotati: " + this.mainController.countDeletedAppointments() + NEWLINE
				+ "Referti: " + this.mainController.countDeletedReports();
	}

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtASLCode,txtCity,txtFacilityCode,txtName,txtStreet,txtStreetNumber);
	}

}
