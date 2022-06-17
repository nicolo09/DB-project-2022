package db.project.view.modify;

import db.project.view.View;

public abstract class ModifyController {
	
	private final View view;
	
	public ModifyController(final View view) {
		this.view = view;
	}
	
	public void goBack() {
		view.goToModifyMenu();
	}
	
	public abstract void addElement();
	
	public abstract void updateElement();

}
