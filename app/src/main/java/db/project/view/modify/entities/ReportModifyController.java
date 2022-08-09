package db.project.view.modify.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.DoctorImpl;
import db.project.model.PatientImpl;
import db.project.model.SurgeryReportImpl;
import db.project.model.VisitReportImpl;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportModifyController extends ModifyController{
	
	private final static String SEPARATOR = ":";
	
	@FXML
    private ComboBox<String> combType;

    @FXML
    private TextField txtCF;

    @FXML
    private TextField txtCodeHospital;

    @FXML
    private TextField txtCodeReport;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextArea txtDoctors;

    @FXML
    private TextField txtDuration;

    @FXML
    private DatePicker txtIssueDate;

    @FXML
    private TextField txtOutcome;

    @FXML
    private TextField txtProcedure;

    @FXML
    private TextField txtTreatment;

	public ReportModifyController(final Command exit, final Controller mainController, final Selector selector) {
		super(exit, mainController, selector);
	}

	@Override
	@FXML
	protected void addElement() {
		Date issueDate = null;
		
		try {
			issueDate = Date.from(Instant.from(txtIssueDate.getValue().atStartOfDay(ZoneId.systemDefault())));
		}
		catch (Exception e) {}
		
		var description = txtDescription.getText().trim() != "" ? txtDescription.getText().trim() : null;
		
		var type = !Objects.isNull(combType.getValue()) ? combType.getValue().trim() : null;
		
		Optional<String> treatment = txtTreatment.getText().trim() != "" ? Optional.of(txtTreatment.getText().trim()) : Optional.empty();
		
		Optional<String> procedure = txtProcedure.getText().trim() != "" ? Optional.of(txtProcedure.getText().trim()) : Optional.empty();
		
		Optional<String> outcome = txtOutcome.getText().trim() != "" ? Optional.of(txtOutcome.getText().trim()) : Optional.empty();
		
		Optional<Integer> duration = isInteger(txtDuration.getText().trim()) ? Optional.of(Integer.parseInt(txtDuration.getText().trim())) : Optional.empty();
		
		var hospitalCode = isInteger(txtCodeHospital.getText().trim()) ? Integer.parseInt(txtCodeHospital.getText().trim()) : INVALID_INT;
		
		var patient = txtCF.getText().trim() != "" && txtCF.getText().trim().length() == CFLENGHT ? txtCF.getText().trim() : null;
		
		List<String> doctors = txtDoctors.getText().trim() != "" ? List.of(txtDoctors.getText().trim().replaceAll("\\s", "").split(SEPARATOR)) : List.of();
		checkDoctorCF(doctors);
		
		showOutcome(this.mainController.insertReport(issueDate, description, type, treatment, procedure, outcome, duration, hospitalCode, patient, doctors));
	}

	@Override
	@FXML
	protected void removeElement() {
		var reportCode = isInteger(txtCodeReport.getText().trim()) ? Integer.parseInt(txtCodeReport.getText().trim()) : INVALID_INT;
		
		showOutcome(this.mainController.removeReport(reportCode));
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
	
	@FXML
	private void initialize() {
		setTextFormatter(txtCF, CF_FORMATTER);
		setTextFormatter(txtCodeHospital, NUMBER_FORMATTER);
		setTextFormatter(txtCodeReport, NUMBER_FORMATTER);
		setTextFormatter(txtDuration, NUMBER_FORMATTER);
		setTextFormatter(txtDoctors,DOCTORS_FORMATTER);
		setTextFormatter(txtDescription, COMPLETE_FORMATTER);
		setTextFormatter(txtOutcome, SIMPLE_FORMATTER);
		setTextFormatter(txtProcedure, SIMPLE_FORMATTER);
		setTextFormatter(txtTreatment, COMPLETE_FORMATTER);
	}
	
	@FXML
    private void selectDoctorsCF() {
		var person = this.selector.selectDoctor();
    	if(Objects.nonNull(person) && person instanceof DoctorImpl) {
    		var doctor = (DoctorImpl) person;
    		if(!txtDoctors.getText().contains(doctor.getCF())) {
    			txtDoctors.setText(txtDoctors.getText() + doctor.getCF() + SEPARATOR + NEWLINE);
    		}
    	}
    }
	
	@FXML
	private void clearDoctorsCF() {
		txtDoctors.setText("");
	} 

    @FXML
    private void selectElement() {
    	var report = this.selector.selectReport();
    	if(Objects.nonNull(report)) {
    		txtCF.setText(report.getPatient().getCF());
    		txtCodeHospital.setText(report.getHospital().getCode().toString());
    		txtCodeReport.setText(report.getCode().toString());
    		txtDescription.setText(report.getDescription());
    		txtDoctors.setText(String.join(SEPARATOR, report.getInvolvedDoctors().stream().map(doctor -> doctor.getCF()).collect(Collectors.toList())) + SEPARATOR);
    		txtIssueDate.setAccessibleText(report.getDate().toString());
    		
    		if(report instanceof VisitReportImpl) {
    		var visit = (VisitReportImpl) report;	
    			txtTreatment.setText(visit.getTherapy());
    			combType.setValue("Visita");
    		} else {
    		var surgery = (SurgeryReportImpl) report;
    			txtDuration.setText(surgery.getDuration().toString());
    			txtOutcome.setText(surgery.getOutcome());
    			txtProcedure.setText(surgery.getProcedure());
    			combType.setValue("Intervento");
    		}
    	}
    }

    @FXML
    private void selectHospital() {
    	var hospital = this.selector.selectHospital();
    	if(Objects.nonNull(hospital)) {
    		txtCodeHospital.setText(hospital.getCode().toString());
    	}
    }

    @FXML
    private void selectPersonCF() {
    	var person = this.selector.selectPatient();
    	if(Objects.nonNull(person) && person instanceof PatientImpl) {
    		var patient = (PatientImpl) person;
    		
    		txtCF.setText(patient.getCF());
    	}
    }

	@Override
	@FXML
	protected void clearAll() {
		this.clearText(txtCF,txtCodeHospital,txtCodeReport,txtDescription,txtDoctors,txtDuration,txtOutcome,txtProcedure,txtTreatment);
		this.clearDate(txtIssueDate);
	}

}
