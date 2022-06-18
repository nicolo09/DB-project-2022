package db.project.view.modify;

import java.io.IOException;

import db.project.view.View;
import db.project.view.modify.entities.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainModifyViewImpl implements MainModifyView {
	
	private final static String PATH = "/Modify_Menu.fxml";
	private final View view;
	private final Stage stage;
	private Parent parent;
	
	public MainModifyViewImpl(final View view, final Stage stage) {
		this.stage = stage;
		this.view = view;
		final FXMLLoader loader = new FXMLLoader();
		final MainModifyController controller = new MainModifyControllerImpl(view, this);
		loader.setController(controller);
		loader.setLocation(getClass().getResource(PATH));
		try {
			this.parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		final Scene scene = new Scene(this.parent);
		this.stage.setScene(scene);
		this.stage.show();
		this.stage.setMinWidth(scene.getWidth());
		this.stage.setMinHeight(scene.getHeight());
	}

	@Override
	public void toAmministratives() {
		final ModifyController controller = new AmministrativeModifyController(this.view);
		final SimpleView amministrativeView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Amministratives.fxml");
		
		amministrativeView.show();		
	}

	@Override
	public void toAppointment() {
		final ModifyController controller = new AppointmentModifyController(this.view);
		final SimpleView appointmentView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Appointment.fxml");
		
		appointmentView.show();
	}

	@Override
	public void toASL() {
		final ModifyController controller = new ASLModifyController(this.view);
		final SimpleView aslView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_ASL.fxml");
		
		aslView.show();		
	}

	@Override
	public void toCure() {
		final ModifyController controller = new CureModifyController(this.view);
		final SimpleView cureView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Cure.fxml");
		
		cureView.show();			
	}

	@Override
	public void toEquipment() {
		final ModifyController controller = new EquipmentModifyController(this.view);
		final SimpleView equipmentView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Equipment.fxml");
		
		equipmentView.show();		
	}

	@Override
	public void toHealthcare() {
		final ModifyController controller = new HealthCareModifyController(this.view);
		final SimpleView healtcareView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Healthcare_worker.fxml");
		
		healtcareView.show();			
	}

	@Override
	public void toHospital() {
		final ModifyController controller = new HospitalModifyController(this.view);
		final SimpleView hospitalView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Hospital.fxml");
		
		hospitalView.show();
	}

	@Override
	public void toPatient() {
		final ModifyController controller = new PatientModifyController(this.view);
		final SimpleView patientView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Patient.fxml");
		
		patientView.show();		
	}

	@Override
	public void toPerson() {
		final ModifyController controller = new PersonModifyController(this.view);
		final SimpleView personView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Person.fxml");
		
		personView.show();			
	}

	@Override
	public void toPhone() {
		final ModifyController controller = new PhoneModifyController(this.view);
		final SimpleView phoneView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Phone.fxml");
		
		phoneView.show();			
	}

	@Override
	public void toReport() {
		final ModifyController controller = new ReportModifyController(this.view);
		final SimpleView reportView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Report.fxml");
		
		reportView.show();		
	}

	@Override
	public void toRoom() {
		final ModifyController controller = new RoomModifyController(this.view);
		final SimpleView roomView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Room.fxml");
		
		roomView.show();		
	}

	@Override
	public void toUO() {
		final ModifyController controller = new UOModifyController(this.view);
		final SimpleView uoView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_UO.fxml");
		
		uoView.show();		
	}
	
	@Override
	public void toWorking() {
		final ModifyController controller = new WorkingModifyController(this.view);
		final SimpleView workingView = new GenericModifyViewImpl(controller, this.stage, "/add/ADD_Working_Status.fxml");
		
		workingView.show();
	}

}
