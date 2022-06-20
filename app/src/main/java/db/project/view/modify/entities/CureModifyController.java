package db.project.view.modify.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CureModifyController extends ModifyController{

	@FXML
    private DatePicker entryDate;

    @FXML
    private DatePicker exitDate;

    @FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextArea txtMotivation;

    @FXML
    private TextField txtNameUO;

	public CureModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	@FXML
	public void addElement() {
		Date entry = Date.from(Instant.from(entryDate.getValue().atStartOfDay(ZoneId.systemDefault())));
		this.mainController.insertCure(null, 0, null, null, null, null);
	}

	@Override
	@FXML
	public void updateElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@FXML
	protected void removeElement() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
    void selectCure() {

    }

    @FXML
    void selectPatientCF() {

    }

    @FXML
    void selectUO() {

    }

}
