package db.project.view.search.referti;

import java.util.stream.Collectors;

import db.project.model.Person;
import db.project.model.Report;
import db.project.model.SurgeryReportImpl;
import db.project.model.VisitReportImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportDetailsControllerImpl {

    private Report report;

    public ReportDetailsControllerImpl(final Report report) {
        this.report = report;
    }

    @FXML
    private ListView<String> listMedici;

    @FXML
    private TextField textCodice;

    @FXML
    private TextField textDate;

    @FXML
    private TextArea textDescription;

    @FXML
    private TextField textDuration;

    @FXML
    private TextField textHospital;

    @FXML
    private TextField textOutcome;

    @FXML
    private TextField textPatient;

    @FXML
    private TextField textProcedure;

    @FXML
    private TextField textTherapy;

    @FXML
    private TextField textType;

    @FXML
    private void initialize(){
        textCodice.setText(report.getCode().toString());
        textPatient.setText(report.getPatient().getName() + " " + report.getPatient().getSurname() + " (" + report.getPatient().getCF() + ")");
        textDate.setText(report.getDate().toString());
        textHospital.setText(report.getHospital().getCode() + " - " + report.getHospital().getName());
        textDescription.setText(report.getDescription());
        listMedici.getItems().addAll(report.getInvolvedDoctors().stream().map(a -> a.getName() + " " + a.getSurname() + " (" + a.getCF() + ")").collect(Collectors.toList()));
        if (report instanceof SurgeryReportImpl) {
            textType.setText("Operazione");
            textTherapy.setText("---");
            textProcedure.setText(((SurgeryReportImpl) report).getProcedure());
            textDuration.setText(((SurgeryReportImpl) report).getDuration().toString());
            textOutcome.setText(((SurgeryReportImpl) report).getOutcome());
        } else if (report instanceof VisitReportImpl) {
            textType.setText("Visita");
            textTherapy.setText(((VisitReportImpl) report).getTherapy());
            textProcedure.setText("---");
            textDuration.setText("---");
            textOutcome.setText("---");
        }
    }

}
