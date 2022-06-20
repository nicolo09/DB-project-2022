package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PhoneModifyController extends ModifyController{

	@FXML
    private TextField txtCF;

    @FXML
    private TextField txtPhoneNumber;

	public PhoneModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var phone = txtPhoneNumber.getText().trim() != "" ? txtPhoneNumber.getText().trim() : null;
		
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		this.mainController.insertPhone(phone, cf);
	}

	@Override
	@FXML
	protected void removeElement() {
		var phone = txtPhoneNumber.getText().trim() != "" ? txtPhoneNumber.getText().trim() : null;
		
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		this.mainController.removePhone(phone, cf);
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
