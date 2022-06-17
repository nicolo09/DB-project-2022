package db.project.model;

import java.util.Date;

public class VisitReportImpl extends AbstractReport implements Report {

    private final String therapy;

    public VisitReportImpl(final Integer code, final Date date, final String description, final Hospital hospital,
            final Person patient, final String therapy) {
        super(code, date, description, hospital, patient);
        this.therapy = therapy;
    }

    public String getTherapy() {
        return this.therapy;
    }

}
