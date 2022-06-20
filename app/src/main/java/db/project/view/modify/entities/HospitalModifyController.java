package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class HospitalModifyController extends ModifyController{

	

	public HospitalModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertHospital(null, null, null, 0, 0);
	}

	@Override
	public void updateElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void removeElement() {
		// TODO Auto-generated method stub
		
	}

}
