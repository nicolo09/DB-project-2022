package db.project.model;

import java.time.LocalDate;

public interface Cure {
    
    Person getPatient();

    Uo getUo();

    LocalDate getDateIn();

    LocalDate getDateOut();

    String getReason();
}
