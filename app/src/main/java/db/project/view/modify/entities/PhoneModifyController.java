package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PhoneModifyController extends ModifyController{

	@FXML
    private TextField txtCF;

    @FXML
    private TextField txtPhoneNumber;

	public PhoneModifyController(Command exit, Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var phone = txtPhoneNumber.getText().trim() != "" ? txtPhoneNumber.getText().trim() : null;
		
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		showOutcome(this.mainController.insertPhone(phone, cf));
	}

	@Override
	@FXML
	protected void removeElement() {
		var phone = txtPhoneNumber.getText().trim() != "" ? txtPhoneNumber.getText().trim() : null;
		
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		showOutcome(this.mainController.removePhone(phone, cf));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtPhoneNumber, NUMBER_FORMATTER);
	}

	@FXML
    void selectElement() {
		//TODO
    }

    @FXML
    void selectPersonCF() {
    	//TODO
    }

}
