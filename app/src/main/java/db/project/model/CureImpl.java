package db.project.model;

import java.time.LocalDate;

public class CureImpl implements Cure {

    private final Person patient;
    private final Uo uo;
    private final LocalDate dateIn;
    private final LocalDate dateOut;
    private final String reason;

    public CureImpl(final Person patient, final Uo uo, final LocalDate dateIn, final LocalDate dateOut, final String reason) {
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
    public LocalDate getDateOut() {
        return dateOut;
    }

    @Override
    public String getReason() {
        return reason;
    }

}
