package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class PatientModifyController extends ModifyController{

	

	public PatientModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertPatient(null, null, null, null, null);
	}

	@Override
	public void updateElement() {
		// TODO Auto-generated method stub
		
	}

}
