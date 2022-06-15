package db.project.view;

import javafx.stage.Stage;

public class ViewImpl implements View {
	
	private final Stage stage;
	
	public ViewImpl(final Stage stage) {
		this.stage = stage;
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
        // TODO Auto-generated method stub

    }

    
    
}
