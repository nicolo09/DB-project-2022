package db.project.view.modify;

import db.project.Command;
import db.project.controller.Controller;
import javafx.fxml.FXML;

public abstract class ModifyController {
	
	protected final static int CFLENGHT = 16;
	
	private final Command exit;
	protected final Controller mainController;
	
	public ModifyController(final Command exit, final Controller mainController) {
		this.exit = exit;
		this.mainController = mainController;
	}
	
	@FXML
	protected void goBack() {
		exit.execute();
	}
	
	protected abstract void addElement();
	
	protected abstract void removeElement();
	
	protected void updateElement() {
		//updatable elements must override this method
	}
	
	protected boolean isInteger(final String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
		
	}

}
