package db.project.model;

import java.util.Collection;
import java.util.Date;

public class VisitReportImpl extends AbstractReport {

    private final String therapy;

    public VisitReportImpl(final Integer code, final Date date, final String description, final Hospital hospital,
            final Person patient, final String therapy, Collection<Person> involvedDoctors) {
        super(code, date, description, hospital, patient, involvedDoctors);
        this.therapy = therapy;
    }

    public String getTherapy() {
        return this.therapy;
    }

}
