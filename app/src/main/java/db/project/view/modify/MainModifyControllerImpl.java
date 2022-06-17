package db.project.view.modify;

import db.project.view.View;
import javafx.fxml.FXML;

public class MainModifyControllerImpl implements MainModifyController {
	
	private final View view;
	private final MainModifyView modifyView;
	
	public MainModifyControllerImpl(final View view, final MainModifyView modifyView) {
		this.view = view;
		this.modifyView = modifyView;
	}

	@Override
	@FXML
	public void goToAmministratives() {
		modifyView.toAmministratives();
	}

	@Override
	@FXML
	public void goToAppointment() {
		modifyView.toAppointment();
	}

	@Override
	@FXML
	public void goToASL() {
		modifyView.toASL();
	}

	@Override
	@FXML
	public void goToCure() {
		modifyView.toCure();
	}

	@Override
	@FXML
	public void goToEquipment() {
		modifyView.toEquipment();
	}

	@Override
	@FXML
	public void goToHealthcare() {
		modifyView.toHealthcare();
	}

	@Override
	@FXML
	public void goToHospital() {
		modifyView.toHospital();
	}

	@Override
	@FXML
	public void goToPatient() {
		modifyView.toPatient();
	}

	@Override
	@FXML
	public void goToPerson() {
		modifyView.toPerson();
	}

	@Override
	@FXML
	public void goToPhone() {
		modifyView.toPhone();
	}

	@Override
	@FXML
	public void goToReport() {
		modifyView.toReport();
	}

	@Override
	@FXML
	public void goToRoom() {
		modifyView.toRoom();
	}

	@Override
	@FXML
	public void goToUO() {
		modifyView.toUO();
	}

	@Override
	@FXML
	public void goBack() {
		view.goToMainMenu();
	}

}
