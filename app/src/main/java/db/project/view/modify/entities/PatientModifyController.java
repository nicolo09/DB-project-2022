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

	public PatientModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
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
	private void selectASL() {
		//TODO
	}

	@FXML
	private void selectElement() {
		//TODO
	}

	@FXML
	private void selectPersonCF() {
		//TODO
	}

}
