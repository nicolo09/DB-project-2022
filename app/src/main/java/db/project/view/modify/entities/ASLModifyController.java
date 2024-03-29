package db.project.view.modify.entities;

import java.util.Objects;
import java.util.Optional;

import db.project.controller.Controller;
import db.project.utils.Command;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
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

	public ASLModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	public void addElement() {
		
		var name = txtName.getText().trim() != "" ? txtName.getText().trim() : null;
		
		var city = txtCity.getText().trim() != "" ? txtCity.getText().trim() : null;
		
		var street = txtStreet.getText().trim() != "" ? txtStreet.getText().trim() : null;
		
		var streetNumber = isInteger(txtStreetNumber.getText().trim()) ? Integer.parseInt(txtStreetNumber.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.insertASL(name, city, street, streetNumber));
	}

	@Override
	@FXML
	public void updateElement() {
		
		var codeASL = isInteger(txtCodeASL.getText().trim()) ? Integer.parseInt(txtCodeASL.getText().trim()) : INVALID_INT;
		
		Optional<String> name = txtName.getText().trim() != "" ? Optional.of(txtName.getText().trim()) : Optional.empty();
		
		Optional<String> city = txtCity.getText().trim() != "" ? Optional.of(txtCity.getText().trim()) : Optional.empty();
		
		Optional<String> street = txtStreet.getText().trim() != "" ? Optional.of(txtStreet.getText().trim()) : Optional.empty();
		
		Optional<Integer> streetNumber = isInteger(txtStreetNumber.getText().trim()) ? Optional.of(Integer.parseInt(txtStreetNumber.getText().trim())) : Optional.empty();
		
		showOutcome(this.mainController.updateASL(codeASL, name, city, street, streetNumber));
	}

	@Override
	@FXML
	protected void removeElement() {
		
		var codeASL = isInteger(txtCodeASL.getText().trim()) ? Integer.parseInt(txtCodeASL.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.removeASL(codeASL));
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCodeASL, NUMBER_FORMATTER);
		setTextFormatter(txtCity, SIMPLE_FORMATTER);
		setTextFormatter(txtStreet, SIMPLE_FORMATTER);
		setTextFormatter(txtStreetNumber, NUMBER_FORMATTER);
		setTextFormatter(txtName, COMPLETE_FORMATTER);
	}
	
	@FXML
    void selectASL() {
		var asl = this.selector.selectAsl();
		if(Objects.nonNull(asl)) {
			this.clearAll();
			txtCodeASL.setText(asl.getCode().toString());
			txtName.setText(asl.getName());
			txtCity.setText(asl.getCity());
			txtStreet.setText(asl.getWay());
			txtStreetNumber.setText(asl.getNumber().toString());
		}
		
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCity,txtCodeASL,txtName,txtStreet,txtStreetNumber);
	}

}
