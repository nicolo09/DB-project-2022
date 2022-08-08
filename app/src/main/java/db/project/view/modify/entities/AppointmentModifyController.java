package db.project.view.modify.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.DoctorImpl;
import db.project.model.PatientImpl;
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
	
	private final static String TIME_FORMATTER = "[0-9]{0,2}";
	private final static String SEPARATOR = ":";

	@FXML
    private DatePicker txtAppointmentDate;

    @FXML
    private TextField txtAppointmentDuration;

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

	public AppointmentModifyController(final Command exit, final Controller mainController, final Selector selector) {
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
		
		List<String> doctors = txtDoctorCF.getText().trim() != "" ? List.of(txtDoctorCF.getText().trim().replaceAll("\\s", "").split(SEPARATOR)) : List.of();
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
		setTextFormatter(txtHours, TIME_FORMATTER);
		setTextFormatter(txtMinutes, TIME_FORMATTER);
	}
	
	@FXML
    private void selectAppointment() {
		var appointment = this.selector.selectAppointment();
		if(Objects.nonNull(appointment)) {
			
			txtAppointmentDuration.setText(appointment.getDuration().toString());
			txtAppointmentType.setText(appointment.getType());
			txtAppointmentDate.setAccessibleText(appointment.getDateTime().toLocalDate().toString());
			txtHours.setText("" + appointment.getDateTime().toLocalTime().getHour());
			txtMinutes.setText("" + appointment.getDateTime().toLocalTime().getMinute());
			
			txtCodeHospital.setText(appointment.getRoom().getHospital().getCode().toString());
			txtRoomNumber.setText(appointment.getRoom().getRoomNumber().toString());
			
			txtPatientCF.setText(appointment.getPatient().getCF());
			txtDoctorCF.setText(String.join(SEPARATOR, appointment.getDoctors().stream().map(doctor -> doctor.getCF()).collect(Collectors.toList())) + SEPARATOR);
		}
    }

    @FXML
    private void selectDoctorCF() {
    	var person = this.selector.selectDoctor();
    	if(Objects.nonNull(person) && person instanceof DoctorImpl) {
    		var doctor = (DoctorImpl) person;
    		if(!txtDoctorCF.getText().contains(doctor.getCF())) {
    			txtDoctorCF.setText(txtDoctorCF.getText() + doctor.getCF() + SEPARATOR + NEWLINE);
    		}
    	}
    }
    
    @FXML
    private void clearDoctorCF() {
    	txtDoctorCF.setText("");
    }
    

    @FXML
    private void selectPatientCF() {
    	var person = this.selector.selectPatient();
    	if(Objects.nonNull(person) && person instanceof PatientImpl) {
    		var patient = (PatientImpl) person;
    		
    		txtPatientCF.setText(patient.getCF());
    	}
    }

    @FXML
    private void selectRoom() {
    	var room = isInteger(txtCodeHospital.getText().trim()) 
    			? (this.mainController.getHospital(Integer.parseInt(txtCodeHospital.getText().trim())).isEmpty()
    					? this.selector.selectRoom()
    					: this.selector.selectRoom(this.mainController.getHospital(Integer.parseInt(txtCodeHospital.getText().trim())).get()))
    			: this.selector.selectRoom();
    	if (Objects.nonNull(room)) {
    		txtCodeHospital.setText(room.getHospital().getCode().toString());
    		txtRoomNumber.setText(room.getRoomNumber().toString());
    	}
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
