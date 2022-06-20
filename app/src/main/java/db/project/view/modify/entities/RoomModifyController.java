package db.project.view.modify.entities;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;

public class RoomModifyController extends ModifyController{

	

	public RoomModifyController(Command exit, Controller mainController) {
		super(exit, mainController);
	}

	@Override
	public void addElement() {
		// TODO Auto-generated method stub
		this.mainController.insertRoom(0, 0);
	}

	@Override
	protected void removeElement() {
		// TODO Auto-generated method stub
		
	}



}
