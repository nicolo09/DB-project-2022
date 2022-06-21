package db.project.view.modify.entities;

import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HospitalModifyController extends ModifyController{

	@FXML
    private TextField txtASLCode;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtFacilityCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStreet;

    @FXML
    private TextField txtStreetNumber;

	public HospitalModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	protected void addElement() {
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var city = txtCity.getText().trim() != "" ? txtCity.getText().trim() : null;
		
		var street = txtStreet.getText().trim() != "" ? txtStreet.getText().trim() : null;
		
		var streetNumber = isInteger(txtStreetNumber.getText().trim()) ? Integer.parseInt(txtStreetNumber.getText().trim()) : null;
		
		var aslCode = isInteger(txtASLCode.getText().trim()) ? Integer.parseInt(txtASLCode.getText().trim()) : null; 
		
		this.mainController.insertHospital(name, city, street, streetNumber, aslCode);
	}

	@Override
	@FXML
	protected void updateElement() {
		var structureCode = isInteger(txtFacilityCode.getText().trim()) ? Integer.parseInt(txtFacilityCode.getText().trim()) : null;
		
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		
		Optional<Integer> aslCode = isInteger(txtASLCode.getText().trim()) ? Optional.of(Integer.parseInt(txtASLCode.getText().trim())) : Optional.empty(); 
		
		this.mainController.updateHospital(structureCode, name, aslCode);
	}

	@Override
	@FXML
	protected void removeElement() {
		var structureCode = isInteger(txtFacilityCode.getText().trim()) ? Integer.parseInt(txtFacilityCode.getText().trim()) : null;
		
		this.mainController.removeHospital(structureCode);	
	}
	
	@FXML
    private void selectElement() {
		//TODO
    }

}
