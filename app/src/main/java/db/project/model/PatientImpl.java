package db.project.model;

import java.util.Date;
import java.util.Optional;

public class PatientImpl extends PersonImpl{

    private final Date birthDate;
    private final Optional<ASL> asl;

    public PatientImpl(String name, String surname, String CF, Date birthDate, Optional<ASL> asl) {
        super(name, surname, CF);
        this.birthDate = birthDate;
        this.asl = asl;
    }
 
    public Date getBirthDate() {
        return this.birthDate;
    }

    public Optional<ASL> getAsl() {
        return this.asl;
    }

}
