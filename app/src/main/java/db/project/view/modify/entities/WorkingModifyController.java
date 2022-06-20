package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WorkingModifyController extends ModifyController{

	@FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtUnitName;

	public WorkingModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var unitName = txtUnitName.getText().trim() != "" ? txtUnitName.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		this.mainController.insertWorking(cf, unitName, hospitalCode);
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var unitName = txtUnitName.getText().trim() != "" ? txtUnitName.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : null;
		
		this.mainController.removeWorking(cf, unitName, hospitalCode);
	}
	
	@FXML
    void selectElement() {
		//TODO
    }

    @FXML
    void selectPersonCF() {
    	//TODO
    }

    @FXML
    void selectUO() {
    	//TODO
    }

}
