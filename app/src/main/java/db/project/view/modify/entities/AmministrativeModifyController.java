package db.project.view.modify.entities;

import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.AdministrativeImpl;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AmministrativeModifyController extends ModifyController{

	@FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRole;

	public AmministrativeModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		var role = txtRole.getText().trim() != "" ? txtRole.getText().trim() : null;
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		Optional<String> lastName = txtLastName.getText().trim() != "" ? Optional.of(txtLastName.getText().trim()) : Optional.empty();
		
		showOutcome(this.mainController.insertAmministratives(cf, role , hospitalCode, name, lastName));
	}

	@Override
	@FXML
	protected void updateElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		Optional<String> role = txtRole.getText().trim() != "" ? Optional.of(txtRole.getText().trim()) : Optional.empty();
		Optional<Integer> hospitalCode = isInteger(txtCodeHospital.getText().trim()) 
				? Optional.of(Integer.parseInt(txtCodeHospital.getText().trim())) 
				: Optional.empty();
		showOutcome(this.mainController.updateAmministratives(cf, role, hospitalCode));
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		showOutcome(this.mainController.removeAmministratives(cf));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtName, SIMPLE_FORMATTER);
		setTextFormatter(txtLastName, SIMPLE_FORMATTER);
		setTextFormatter(txtRole, SIMPLE_FORMATTER);
	}
	
	@FXML
    private void selectAmministratives() {
		var person = this.selector.selectAdministrative();
		if(Objects.nonNull(person) && person instanceof AdministrativeImpl) {
			var administrative = (AdministrativeImpl) person;
			
			txtCF.setText(administrative.getCF());
			txtName.setText(administrative.getName());
			txtLastName.setText(administrative.getSurname());
			txtRole.setText(administrative.getRole());
			txtCodeHospital.setText(administrative.getHospital().getCode().toString());
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
		this.clearText(txtCF,txtCodeHospital,txtLastName,txtName,txtRole);
	}

}
