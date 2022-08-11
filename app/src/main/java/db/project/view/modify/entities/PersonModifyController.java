package db.project.view.modify.entities;

import java.util.Objects;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonModifyController extends ModifyController{

	@FXML
	private TextField txtCF;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtName;

	public PersonModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var lastName = txtLastName.getText().trim() != "" ? txtLastName.getText().trim() : null;
		
		showOutcome(this.mainController.insertPerson(cf, name, lastName));
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		showOutcome(this.mainController.removePerson(cf));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtLastName, SIMPLE_FORMATTER);
		setTextFormatter(txtName, SIMPLE_FORMATTER);
	}

	@FXML
    private void selectElement() {
		var person = this.selector.selectPerson();
		if(Objects.nonNull(person)) {
			this.clearAll();
			txtCF.setText(person.getCF());
			txtName.setText(person.getName());
			txtLastName.setText(person.getSurname());
		}
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCF,txtLastName,txtName);
	}
	

}
