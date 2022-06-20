package db.project.model;

import java.util.Collection;
import java.util.Date;


import javafx.print.Collation;

public abstract class AbstractReport implements Report {

    private final Integer code;
    private final Date date;
    private final String description;
    private final Hospital hospital;
    private final Person patient;
    private final Collection<Person> involvedDoctors;

    public AbstractReport(final Integer code, final Date date, final String description, final Hospital hospital,
            final Person patient, Collection<Person> involvedDoctors) {
        // Throws IllegalArgumentException if type is not in REPORT_TYPES
        this.code = code;
        this.date = date;
        this.description = description;
        this.hospital = hospital;
        this.patient = patient;
        this.involvedDoctors = involvedDoctors;
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

    @Override
    public Collection<Person> getInvolvedDoctors() {
        return this.involvedDoctors;
    }
}
