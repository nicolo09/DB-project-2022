package db.project.view.search;

import java.io.IOException;

import db.project.controller.Controller;
import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Uo;
import db.project.view.View;
import db.project.view.ViewImpl;
import db.project.view.search.hospital.SearchASLControllerImpl;
import db.project.view.search.hospital.SearchASLView;
import db.project.view.search.hospital.SearchASLViewImpl;
import db.project.view.search.hospital.SearchEquipmentView;
import db.project.view.search.hospital.SearchHospitalView;
import db.project.view.search.hospital.SearchHospitalViewImpl;
import db.project.view.search.hospital.SearchRoomControllerImpl;
import db.project.view.search.hospital.SearchRoomView;
import db.project.view.search.hospital.SearchRoomViewImpl;
import db.project.view.search.hospital.SearchUoView;
import db.project.view.search.hospital.SearchHospitalControllerImpl;
import db.project.view.search.hospital.SearchUoControllerImpl;
import db.project.view.search.hospital.SearchUoViewImpl;
import db.project.view.search.person.SearchDoctorControllerImpl;
import db.project.view.search.person.SearchDoctorsViewImpl;
import db.project.view.search.person.SearchAdministrativeControllerImpl;
import db.project.view.search.person.SearchPatientsControllerImpl;
import db.project.view.search.person.SearchPatientsViewImpl;
import db.project.view.search.person.SearchPersonView;
import db.project.view.search.person.SearchPersonViewImpl;
import db.project.view.search.referti.SearchAppointmentsControllerImpl;
import db.project.view.search.referti.SearchAppointmentsView;
import db.project.view.search.referti.SearchAppointmentsViewImpl;
import db.project.view.search.referti.SearchCureControllerImpl;
import db.project.view.search.referti.SearchCureView;
import db.project.view.search.referti.SearchCureViewImpl;
import db.project.view.search.referti.SearchReportsControllerImpl;
import db.project.view.search.referti.SearchRefertiView;
import db.project.view.search.referti.SearchRefertiViewImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import db.project.view.search.hospital.SearchEquipmentViewImpl;
import db.project.view.search.hospital.SearchEquipmentControllerImpl;

public class SearchMainViewImpl implements SearchMainView {

    private static final String PATH = "search_selection.fxml";
    private final View mainView;
    private final Controller mainController;
    private final Stage mainStage;
    private Parent parent;
    private Selector entitySelector;

    public SearchMainViewImpl(View mainView, Controller mainController, Stage stage) {
        this.mainView = mainView;
        this.mainController = mainController;
        this.mainStage = stage;
        this.entitySelector = new SelectorImpl(mainController, mainStage);
    }

    @Override
    public void goToASL() {
        final SearchASLView view = new SearchASLViewImpl(mainStage,
                new SearchASLControllerImpl(() -> this.show(), mainController));
        view.show();
    }

    @Override
    public void goToAppuntamenti() {
        final SearchAppointmentsView view = new SearchAppointmentsViewImpl(mainStage,
                new SearchAppointmentsControllerImpl(() -> this.show(), mainController, this.entitySelector));
        view.show();
    }

    @Override
    public void goToOspedali() {
        final SearchHospitalView view = new SearchHospitalViewImpl(mainStage, new SearchHospitalControllerImpl(
                () -> this.show(), mainController, entitySelector, this::goToModalAttrezzature, this::goToModalRooms));
        view.show();
    }

    public void goToModalAttrezzature(final Hospital hospital) {
        Stage stage = new Stage();
        stage.setTitle("Attrezzature");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);

        final SearchEquipmentView view = new SearchEquipmentViewImpl(stage,
                new SearchEquipmentControllerImpl(hospital, mainController));
        view.showAndWait();
    }

    public void goToModalRooms(final Hospital hospital) {
        Stage stage = new Stage();
        stage.setTitle("Stanze");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);

        final SearchRoomView view = new SearchRoomViewImpl(stage,
                new SearchRoomControllerImpl(hospital, mainController));
        view.showAndWait();
    }

    @Override
    public void goToPazienti() {
        final SearchPersonView view = new SearchPatientsViewImpl(mainStage,
                new SearchPatientsControllerImpl(() -> this.show(), mainController, this.entitySelector));
        view.show();
    }

    @Override
    public void goToPersonaleAmministrativo() {
        final SearchPersonView view = new SearchPersonViewImpl(mainStage,
                new SearchAdministrativeControllerImpl(() -> this.show(), mainController));
        view.show();
    }

    @Override
    public void goToPersonaleSanitario() {
        final SearchPersonView view = new SearchDoctorsViewImpl(mainStage,
                new SearchDoctorControllerImpl(() -> this.show(), mainController));
        view.show();
    }

    @Override
    public void goToReferti() {
        final SearchRefertiView view = new SearchRefertiViewImpl(
                new SearchReportsControllerImpl(this.mainController, entitySelector, this::show, this::showError),
                mainStage);
        view.show();
    }

    @Override
    public void goToRicoveri() {
        final SearchCureView view = new SearchCureViewImpl(mainStage,
                new SearchCureControllerImpl(() -> this.show(), this.mainController, this.entitySelector));
        view.show();
    }

    @Override
    public void goToUnitaOperative() {
        final SearchUoView view = new SearchUoViewImpl(mainStage,
                new SearchUoControllerImpl(() -> this.show(), mainController, entitySelector));
        view.show();
    }

    @Override
    public void show() {
        final FXMLLoader loader = new FXMLLoader();
        SearchMainControllerImpl controller = new SearchMainControllerImpl(this);
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/" + PATH));
        try {
            this.parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Scene scene = new Scene(parent);
        ViewImpl.adjustStageAndSetScene(mainStage, scene);
        this.mainStage.show();
    }

    @Override
    public void goToMainMenu() {
        this.mainView.goToMainMenu();
    }

    @Override
    public Person selectPerson() {
        return this.entitySelector.selectPerson();
    }

    @Override
    public Hospital selectHospital() {
        return this.entitySelector.selectHospital();
    }

    @Override
    public ASL selectAsl() {
        return this.entitySelector.selectAsl();
    }

    @Override
    public Uo selectUo() {
        return this.entitySelector.selectUo();
    }

    @Override
    public void showError(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR, errorMessage);
        alert.showAndWait();
    }

    @Override
    public void goToImpieghi() {
        final SearchImpieghiView view = new SearchImpieghiViewImpl(mainStage,
                new SearchImpieghiControllerImpl(() -> this.show(), mainController, this.entitySelector, this::showError));
        view.show();
    }
}
