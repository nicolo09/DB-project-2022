package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class ReportModifyController extends ModifyController{

	

	public ReportModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertReport(null, null, null, null, null, null, null, 0, null, null);
	}

	@Override
	protected void removeElement() {
		// TODO Auto-generated method stub
		
	}

	

}
