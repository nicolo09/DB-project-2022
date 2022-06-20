package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PersonModifyController extends ModifyController{

	@FXML
	private TextField txtCF;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtName;

	public PersonModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var lastName = txtLastName.getText().trim() != "" ? txtLastName.getText().trim() : null;
		
		this.mainController.insertPerson(cf, name, lastName);
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		this.mainController.removePerson(cf);
	}

	@FXML
    private void selectElement() {
		//TODO
    }

}
