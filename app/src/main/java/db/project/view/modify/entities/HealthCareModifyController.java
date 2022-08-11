package db.project.view.modify.entities;

import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.DoctorImpl;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HealthCareModifyController extends ModifyController{

	@FXML
    private TextField txtCF;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRole;
	
	public HealthCareModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var role = txtRole.getText().trim() != "" ? txtRole.getText().trim() : null;
		
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		
		Optional<String> lastName = txtLastName.getText().trim() != "" ? Optional.of(txtLastName.getText().trim()) : Optional.empty();
		
		showOutcome(this.mainController.insertHealtcare(cf, role, name, lastName));
		
	}

	@Override
	@FXML
	protected void updateElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		Optional<String> role = txtRole.getText().trim() != "" ? Optional.of(txtRole.getText().trim()) : Optional.empty();
		
		showOutcome(this.mainController.updateHealtcare(cf, role));
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		showOutcome(this.mainController.removeHealtcare(cf));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtRole, COMPLETE_FORMATTER);
		setTextFormatter(txtName, SIMPLE_FORMATTER);
		setTextFormatter(txtLastName, SIMPLE_FORMATTER);
	}
	
	@FXML
	private void selectElement() {
		var person = this.selector.selectDoctor();
		if(Objects.nonNull(person) && person instanceof DoctorImpl) {
			var healthcare = (DoctorImpl) person;
			this.clearAll();
			txtCF.setText(healthcare.getCF());
			txtName.setText(healthcare.getName());
			txtLastName.setText(healthcare.getSurname());
			txtRole.setText(healthcare.getRole());
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
		this.clearText(txtCF,txtLastName,txtName,txtRole);
	}

}
