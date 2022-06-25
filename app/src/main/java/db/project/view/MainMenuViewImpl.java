package db.project.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuViewImpl implements MainMenuView {
	
	private final static String PATH = "Main_menu.fxml";
	private final Stage stage;
	private Parent parent;
	
	public MainMenuViewImpl(final MainMenuController controller, final Stage stage) {
		this.stage = stage;
		final FXMLLoader loader = new FXMLLoader();
		loader.setController(controller);
		loader.setLocation(getClass().getResource("/" + PATH));
		try {
			this.parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		final Scene scene = new Scene(parent);
		ViewImpl.adjustStageAndSetScene(stage, scene);
		this.stage.show();
	}

}
