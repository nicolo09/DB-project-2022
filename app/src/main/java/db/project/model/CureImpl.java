package db.project.model;

import java.time.LocalDate;
import java.util.Optional;

public class CureImpl implements Cure {

    private final Person patient;
    private final Uo uo;
    private final LocalDate dateIn;
    private final Optional<LocalDate> dateOut;
    private final String reason;

    public CureImpl(final Person patient, final Uo uo, final LocalDate dateIn, final Optional<LocalDate> dateOut, final String reason) {
        this.patient = patient;
        this.uo = uo;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.reason = reason;
    }

    @Override
    public Person getPatient() {
        return patient;
    }

    @Override
    public Uo getUo() {
        return uo;
    }

    @Override
    public LocalDate getDateIn() {
        return dateIn;
    }

    @Override
    public Optional<LocalDate> getDateOut() {
        return dateOut;
    }

    @Override
    public String getReason() {
        return reason;
    }

}
