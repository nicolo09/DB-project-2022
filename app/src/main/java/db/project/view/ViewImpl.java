package db.project.view;

import db.project.view.modify.MainModifyView;
import db.project.view.modify.MainModifyViewImpl;
import db.project.controller.Controller;
import db.project.view.search.SearchMainView;
import db.project.view.search.SearchMainViewImpl;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewImpl implements View {
	
	private final Stage stage;
	private final Controller mainController;
	
	public ViewImpl(final Controller controller, final Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Hospital explorer");
		this.mainController = controller;
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
        final MainModifyView view = new MainModifyViewImpl(this, mainController, stage);
        view.show();
    }

    public static void adjustStageAndSetScene(final Stage stage, final Scene scene) {
        Platform.runLater(() -> {
            final var width = stage.getWidth();
            final var height = stage.getHeight();
            stage.setMinWidth(scene.getWidth());
            // stage.setWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            // stage.setHeight(scene.getHeight());
            stage.setScene(scene);
            stage.setWidth(scene.getWidth() < width ? width : scene.getWidth());
            stage.setHeight(scene.getHeight() < height ? height : scene.getHeight());
        });
    }

}
