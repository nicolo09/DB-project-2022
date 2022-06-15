package db.project.view;

import db.project.controller.Controller;
import db.project.view.search.SearchMainControllerImpl;
import db.project.view.search.SearchMainView;
import db.project.view.search.SearchMainViewImpl;
import db.project.view.search.referti.SearchRefertiControllerImpl;
import db.project.view.search.referti.SearchRefertiView;
import db.project.view.search.referti.SearchRefertiViewImpl;
import javafx.stage.Stage;

public class ViewImpl implements View {
	
	private final Stage stage;
	private Controller mainController;
	
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
        final SearchMainView view = new SearchMainViewImpl(this, mainController, stage);        
        view.show();
    }

    @Override
    public void goToModifyMenu() {
        // TODO Auto-generated method stub

    }

}
