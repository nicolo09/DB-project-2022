package db.project.view;

import db.project.view.modify.MainModifyView;
import db.project.view.modify.MainModifyViewImpl;
import javafx.stage.Stage;

public class ViewImpl implements View {
	
	private final Stage stage;
	
	public ViewImpl(final Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Hospital explorer");
	}
	
    @Override
    public void goToMainMenu() {
       final MainMenuController mmcontroller = new MainMenuControllerImpl(this);
       final MainMenuView mmview = new MainMenuViewImpl(mmcontroller, stage);
       
       mmview.show();
    }

    @Override
    public void goToSearchMenu() {
        // TODO Auto-generated method stub

    }

    @Override
    public void goToModifyMenu() {
        final MainModifyView mmview = new MainModifyViewImpl(this, stage);
        
        mmview.show();
    }

    
    
}
