package db.project.view.search;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchMainControllerImpl {

    private final SearchMainView view;
    
    public SearchMainControllerImpl(SearchMainView view) {
        this.view = view;
    }

	@FXML
	public void goToMainMenu() {
		view.goToMainMenu();
	}
    
    @FXML
    private void onASL(ActionEvent event) {
        view.goToASL();
    }

    @FXML
    private void onAppuntamenti(ActionEvent event) {
        this.view.goToAppuntamenti();
    }

    @FXML
    private void onOspedali(ActionEvent event) {
        this.view.goToOspedali();
    }

    @FXML
    private void onPazienti(ActionEvent event) {
        this.view.goToPazienti();
    }

    @FXML
    private void onPersonaleAmministrativo(ActionEvent event) {
        this.view.goToPersonaleAmministrativo();
    }

    @FXML
    private void onPersonaleSanitario(ActionEvent event) {
        this.view.goToPersonaleSanitario();
    }

    @FXML
    private void onReferti() {
        this.view.goToReferti();
    }

    @FXML
    private void onRicoveri(ActionEvent event) {
        this.view.goToRicoveri();
    }

    @FXML
    private void onUnitaOperative(ActionEvent event) {
        this.view.goToUnitaOperative();
    }

    @FXML
    private void onImpieghi(ActionEvent event) {
        this.view.goToImpieghi();
    }
    
}
