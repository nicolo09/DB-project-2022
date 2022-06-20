package db.project.model;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class SurgeryReportImpl extends AbstractReport{

    private final String procedure;
    private final String outcome;
    private final Duration duration;

    public SurgeryReportImpl(final Integer code, final Date date, final String description, final Hospital hospital,
            final Person patient, String procedure, String outcome, Duration duration, Collection<Person> involvedDoctors) {
        super(code, date, description, hospital, patient, involvedDoctors);
        this.procedure = procedure;
        this.outcome = outcome;
        this.duration = duration;
    }

    public String getProcedure() {
        return this.procedure;
    }

    public String getOutcome() {
        return this.outcome;
    }

    public Duration getDuration() {
        return this.duration;
    }

}
