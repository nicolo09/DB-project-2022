package db.project.view.search;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SearchMainControllerImpl {

    private final SearchMainView view;
    
    public SearchMainControllerImpl(SearchMainView view) {
        this.view = view;
    }
    
    @FXML
    private void onASL(ActionEvent event) {
        view.goToASL();
    }

    @FXML
    private void onAppuntamenti(ActionEvent event) {
        // TODO Auto-generated method stub
    }

    @FXML
    private void onOspedali(ActionEvent event) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
    }

    @FXML
    private void onUnitaOperative(ActionEvent event) {
        // TODO Auto-generated method stub
    }
    
}
