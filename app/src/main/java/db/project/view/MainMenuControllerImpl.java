package db.project.view;
import javafx.fxml.FXML;

public class MainMenuControllerImpl implements MainMenuController {
	
	private final View view;
	
	public MainMenuControllerImpl(final View view) {
		this.view = view;
	}

	@Override
	@FXML
	public void goToMainMenu() {
		view.goToMainMenu();
	}

	@Override
	@FXML
	public void goToModifyMenu() {
		view.goToModifyMenu();
	}

	@Override
	@FXML
	public void goToSearchMenu() {
		view.goToSearchMenu();
	}

}
