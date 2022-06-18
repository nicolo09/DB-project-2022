package db.project.model;

import java.util.Date;

public abstract class AbstractReport implements Report {

    private final Integer code;
    private final Date date;
    private final String description;
    private final Hospital hospital;
    private final Person patient;

    public AbstractReport(final Integer code, final Date date, final String description, final Hospital hospital,
            final Person patient) {
        // Throws IllegalArgumentException if type is not in REPORT_TYPES
        this.code = code;
        this.date = date;
        this.description = description;
        this.hospital = hospital;
        this.patient = patient;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Hospital getHospital() {
        return this.hospital;
    }

    @Override
    public Person getPatient() {
        return this.patient;
    }
}
