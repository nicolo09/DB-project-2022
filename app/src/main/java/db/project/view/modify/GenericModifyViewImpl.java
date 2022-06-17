package db.project.view.modify;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenericModifyViewImpl implements SimpleView {
	
	private final Stage stage;
	private Parent parent;
	
	public GenericModifyViewImpl(final ModifyController controller, final Stage stage, final String path) {
		this.stage = stage;
		final FXMLLoader loader = new FXMLLoader();
		loader.setController(controller);
		loader.setLocation(getClass().getResource(path));
		try {
			this.parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		final Scene scene = new Scene(parent);
		this.stage.setScene(scene);
		this.stage.show();
		stage.setMinWidth(scene.getWidth());
		stage.setMinHeight(scene.getHeight());
	}

}
