package db.project.view.modify;

import db.project.Command;
import db.project.controller.Controller;

public abstract class ModifyController {
	
	private final Command exit;
	protected final Controller mainController;
	
	public ModifyController(final Command exit, final Controller mainController) {
		this.exit = exit;
		this.mainController = mainController;
	}
	
	public void goBack() {
		exit.execute();
	}
	
	public void selectASL() {
		// overridable method, it isn't implemented by default
	}
	
	public void selectPersonCF() {
		// overridable method, it isn't implemented by default
	}
	
	public void selectPatientCF() {
		// overridable method, it isn't implemented by default
	}
	
	public void selectDoctorCF() {
		// overridable method, it isn't implemented by default
	}
	
	public void selectRoom() {
		// overridable method, it isn't implemented by default
	}
	
	// public abstract void selectExistingElement(); TODO is possible?
	
	public abstract void addElement();
	
	public abstract void updateElement();

}
