package db.project.view.modify.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.PatientImpl;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PatientModifyController extends ModifyController{

	@FXML
    private DatePicker birthDay;

    @FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeASL;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

	public PatientModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		Date birth = !Objects.isNull(birthDay.getValue()) ? Date.from(Instant.from(birthDay.getValue().atStartOfDay(ZoneId.systemDefault()))) : null;
		
		Optional<Integer> aslCode = isInteger(txtCodeASL.getText().trim()) ? Optional.of(Integer.parseInt(txtCodeASL.getText().trim())) : Optional.empty();
		
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		
		Optional<String> lastName = txtLastName.getText().trim() != "" ? Optional.of(txtLastName.getText().trim()) : Optional.empty();

		showOutcome(this.mainController.insertPatient(cf, birth, aslCode, name, lastName));
	}

	@Override
	@FXML
	protected void updateElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		Optional<Integer> aslCode = isInteger(txtCodeASL.getText().trim()) ? Optional.of(Integer.parseInt(txtCodeASL.getText().trim())) : Optional.empty();
		
		showOutcome(this.mainController.updatePatient(cf, aslCode));
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		showOutcome(this.mainController.removePatient(cf));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtCodeASL, NUMBER_FORMATTER);
		setTextFormatter(txtName, SIMPLE_FORMATTER);
		setTextFormatter(txtLastName, SIMPLE_FORMATTER);
	}
	
	@FXML
	private void selectASL() {
		var asl = this.selector.selectAsl();
		if(Objects.nonNull(asl)) {
			txtCodeASL.setText(asl.getCode().toString());
		}
	}

	@FXML
	private void selectElement() {
		var person = this.selector.selectPerson();
		if(Objects.nonNull(person) && person instanceof PatientImpl) {
			var patient = (PatientImpl) person;
			
			txtCF.setText(patient.getCF());
			txtName.setText(patient.getName());
			txtLastName.setText(patient.getSurname());
			birthDay.setAccessibleText(patient.getBirthDate().toString());
			patient.getAsl().ifPresent(asl -> txtCodeASL.setText(asl.getCode().toString()));
		}
	}

	@FXML
	private void selectPersonCF() {
		var person = this.selector.selectPerson();
		if(Objects.nonNull(person)) {
			txtCF.setText(person.getCF());
			txtName.setText(person.getName());
			txtLastName.setText(person.getSurname());
		}
	}

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCF,txtCodeASL,txtLastName,txtName);
		this.clearDate(birthDay);
	}

}
