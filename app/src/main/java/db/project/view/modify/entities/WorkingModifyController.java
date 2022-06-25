package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WorkingModifyController extends ModifyController{

	@FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtUnitName;

	public WorkingModifyController(Command exit, Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var unitName = txtUnitName.getText().trim() != "" ? txtUnitName.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.insertWorking(cf, unitName, hospitalCode));
	}

	@Override
	@FXML
	protected void removeElement() {
		var cf = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		var unitName = txtUnitName.getText().trim() != "" ? txtUnitName.getText().trim() : null;
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.removeWorking(cf, unitName, hospitalCode));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtUnitName, COMPLETE_FORMATTER);
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
