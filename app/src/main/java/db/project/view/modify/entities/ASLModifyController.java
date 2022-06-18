package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class ASLModifyController extends ModifyController{

	

	public ASLModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertASL(null, null, null, 0);
		
	}

	@Override
	public void updateElement() {
		// TODO Auto-generated method stub
		
	}

}
