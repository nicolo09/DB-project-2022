package db.project.model;

import java.time.LocalDate;
import java.util.Optional;

public interface Cure {
    
    Person getPatient();

    Uo getUo();

    LocalDate getDateIn();

    Optional<LocalDate> getDateOut();

    String getReason();
}
