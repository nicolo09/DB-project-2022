package db.project.view.modify.entities;

import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ASLModifyController extends ModifyController{

	@FXML
    private TextField txtCity;

    @FXML
    private TextField txtCodeASL;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStreet;

    @FXML
    private TextField txtStreetNumber;

	public ASLModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	public void addElement() {
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var city = txtCity.getText().trim() != "" ? txtCity.getText().trim() : null;
		
		var street = txtStreet.getText().trim() != "" ? txtStreet.getText().trim() : null;
		
		var streetNumber = isInteger(txtStreetNumber.getText().trim()) ? Integer.parseInt(txtStreetNumber.getText().trim()) : null;
		
		this.mainController.insertASL(name, city, street, streetNumber);
		
	}

	@Override
	@FXML
	public void updateElement() {
		
		var codeASL = isInteger(txtCodeASL.getText().trim()) ? Integer.parseInt(txtCodeASL.getText().trim()) : null;
		
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		
		Optional<String> city = txtCity.getText().trim() != "" ? Optional.of(txtCity.getText().trim()) : Optional.empty();
		
		Optional<String> street = txtStreet.getText().trim() != "" ? Optional.of(txtStreet.getText().trim()) : Optional.empty();
		
		Optional<Integer> streetNumber = isInteger(txtStreetNumber.getText().trim()) ? Optional.of(Integer.parseInt(txtStreetNumber.getText().trim())) : Optional.empty();
		
		this.mainController.updateASL(codeASL, name, city, street, streetNumber);
	}

	@Override
	@FXML
	protected void removeElement() {
		
		var codeASL = isInteger(txtCodeASL.getText().trim()) ? Integer.parseInt(txtCodeASL.getText().trim()) : null;
		
		this.mainController.removeASL(codeASL);
	}
	
	@FXML
    void selectASL() {
		//TODO
    }

}
