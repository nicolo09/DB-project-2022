package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class PhoneModifyController extends ModifyController{

	

	public PhoneModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertPhone(null, null);
	}

	@Override
	public void updateElement() {
		// TODO Auto-generated method stub
		
	}

}
