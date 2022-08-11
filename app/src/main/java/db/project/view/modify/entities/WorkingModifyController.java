package db.project.view.modify.entities;

import java.util.Objects;

import db.project.controller.Controller;
import db.project.model.DoctorImpl;
import db.project.utils.Command;
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

	public WorkingModifyController(final Command exit, final Controller mainController, final Selector selector) {
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
		var job = this.selector.selectJob();
		if(Objects.nonNull(job)) {
			this.clearAll();
			txtCF.setText(job.getValue().getCF());
			txtCodeHospital.setText(job.getKey().getHospital().getCode().toString());
			txtUnitName.setText(job.getKey().getName());
		}
    }

    @FXML
    void selectPersonCF() {
    	var person = this.selector.selectAdministrative();
    	if(Objects.nonNull(person) && person instanceof DoctorImpl) {
    		var healthcare = (DoctorImpl) person;
    		
    		txtCF.setText(healthcare.getCF());
    	}
    }

    @FXML
    void selectUO() {
    	var operative_unit = this.selector.selectUo();
    	if(Objects.nonNull(operative_unit)) {
    		txtCodeHospital.setText(operative_unit.getHospital().getCode().toString());
    		txtUnitName.setText(operative_unit.getName());
    	}
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCF,txtCodeHospital,txtUnitName);
	}

}
