package db.project.view.modify.entities;

import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
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

	public AmministrativeModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().length() == CFLENGHT ? txtCF.getText().trim() : null;
		var role = txtRole.getText().trim() != "" ? txtRole.getText().trim() : null;
		var hospitalCode = txtCodeHospital.getText().trim() != "" && isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		Optional<String> lastName = txtLastName.getText().trim() != "" ? Optional.of(txtLastName.getText().trim()) : Optional.empty();
		
		this.mainController.insertAmministratives(cf, role , hospitalCode, name, lastName);		
	}

	@Override
	@FXML
	protected void updateElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().length() == CFLENGHT ? txtCF.getText().trim() : null;
		Optional<String> role = txtRole.getText().trim() != "" ? Optional.of(txtRole.getText().trim()) : Optional.empty();
		Optional<Integer> hospitalCode = txtCodeHospital.getText().trim() != "" && isInteger(txtCodeHospital.getText().trim()) 
				? Optional.of(Integer.parseInt(txtCodeHospital.getText().trim())) 
				: Optional.empty();
		this.mainController.updateAmministratives(cf, role, hospitalCode);
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().length() == CFLENGHT ? txtCF.getText().trim() : null;
		this.mainController.removeAmministratives(cf);
	}
	
	@FXML
    private void selectAmministratives() {
		//TODO
    }

    @FXML
    private void selectPersonCF() {
    	//TODO
    }

}
