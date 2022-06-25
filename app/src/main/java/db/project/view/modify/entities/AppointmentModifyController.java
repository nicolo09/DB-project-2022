package db.project.view.modify.entities;

import java.sql.Timestamp;
import java.util.List;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AppointmentModifyController extends ModifyController{
	
	private final static int MAX_HOUR = 24;
	private final static int MIN_HOUR = 0;
	
	private final static int MAX_MINUTE = 60;
	private final static int MIN_MINUTE = 0;

	@FXML
    private DatePicker txtAppointmentDate;

    @FXML
    private TextField txtAppointmentDuration;

    //TODO change with datatimepicker
    @FXML
    private TextField txtHours;

    @FXML
    private TextField txtMinutes;

    @FXML
    private TextField txtAppointmentType;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextArea txtDoctorCF;

    @FXML
    private TextField txtPatientCF;

    @FXML
    private TextField txtRoomNumber;

	public AppointmentModifyController(Command exit, Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var roomNumber = isInteger(txtRoomNumber.getText().trim()) ? Integer.parseInt(txtRoomNumber.getText().trim()) : INVALID_INT;
		
		int hour = isInteger(txtHours.getText().trim()) ? Integer.parseInt(txtHours.getText().trim()) : 0;
		int minutes = isInteger(txtMinutes.getText().trim()) ? Integer.parseInt(txtMinutes.getText().trim()) : 0;
		Timestamp date = null;
		
		if(hour < MAX_HOUR && hour >= MIN_HOUR && minutes < MAX_MINUTE && minutes >= MIN_MINUTE) {
			try {
				date = Timestamp.valueOf(txtAppointmentDate.getValue().atTime(hour, minutes));
			}
			catch (Exception e) {}
		}
		
		var duration = isInteger(txtAppointmentDuration.getText().trim()) ? Integer.parseInt(txtAppointmentDuration.getText().trim()) : INVALID_INT;
		
		var type = txtAppointmentType.getText().trim() != "" ? txtAppointmentType.getText().trim() : null;
		
		var patient = txtPatientCF.getText().trim() != "" && txtPatientCF.getText().trim().length() == CFLENGHT ? txtPatientCF.getText().trim() : null;
		
		List<String> doctors = txtDoctorCF.getText().trim() != "" ? List.of(txtDoctorCF.getText().trim().split(":")) : List.of();
		checkDoctorCF(doctors);
		
		showOutcome(this.mainController.insertAppointment(hospitalCode, roomNumber, date, duration, type, patient, doctors));
		
	}

	@Override
	@FXML
	protected void removeElement() {
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var roomNumber = isInteger(txtRoomNumber.getText().trim()) ? Integer.parseInt(txtRoomNumber.getText().trim()) : INVALID_INT;
		
		int hour = isInteger(txtHours.getText().trim()) ? Integer.parseInt(txtHours.getText().trim()) : 0;
		int minutes = isInteger(txtMinutes.getText().trim()) ? Integer.parseInt(txtMinutes.getText().trim()) : 0;
		Timestamp date = null;
		
		if(hour < MAX_HOUR && hour >= MIN_HOUR && minutes < MAX_MINUTE && minutes >= MIN_MINUTE) {
			try {
				date = Timestamp.valueOf(txtAppointmentDate.getValue().atTime(hour, minutes));
			}
			catch (Exception e) {}
		}
		
		showOutcome(this.mainController.removeAppointment(hospitalCode, roomNumber, date));		
	}
	
	@FXML
	private void initialize() {
		setTextFormatter(txtAppointmentDuration, NUMBER_FORMATTER);
		setTextFormatter(txtAppointmentType, SIMPLE_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtPatientCF, CF_FORMATTER);
		setTextFormatter(txtRoomNumber, NUMBER_FORMATTER);
		setTextFormatter(txtDoctorCF, DOCTORS_FORMATTER);
	}
	
	@FXML
    private void selectAppointment() {
		//TODO
    }

    @FXML
    private void selectDoctorCF() {
    	//TODO
    }

    @FXML
    private void selectPatientCF() {
    	//TODO
    }

    @FXML
    private void selectRoom() {
    	//TODO
    }
    
    private void checkDoctorCF(List<String> doctors){
    	boolean nullify = doctors.size() == 0 ? true : false;
    	for (String doctor : doctors) {
			if(doctor.length() != CFLENGHT) {
				nullify = true;
			}
		}
    	if(nullify) {
    		doctors = null;    		
    	}
    }

	

}
