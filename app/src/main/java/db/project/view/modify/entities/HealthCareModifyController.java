package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class HealthCareModifyController extends ModifyController{

	
	public HealthCareModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertHealtcare(null, null, null, null);
		
	}

	@Override
	public void updateElement() {
		// TODO Auto-generated method stub
		
	}

}
