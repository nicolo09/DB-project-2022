package db.project.view.modify.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.project.Command;
import db.project.controller.Controller;
import db.project.view.modify.ModifyController;
import db.project.view.search.Selector;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportModifyController extends ModifyController{
	
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

	public ReportModifyController(Command exit, Controller mainController, final Selector selector) {
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
		
		List<String> doctors = txtDoctors.getText().trim() != "" ? List.of(txtDoctors.getText().trim().split(":")) : List.of();
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
		setTextFormatter(txtTreatment, SIMPLE_FORMATTER);
	}
	
	@FXML
    void selectDoctorsCF() {
		//TODO
    }

    @FXML
    void selectElement() {
    	//TODO
    }

    @FXML
    void selectHospital() {
    	//TODO
    }

    @FXML
    void selectPersonCF() {
    	//TODO
    }

}
