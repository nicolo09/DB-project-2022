package db.project.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public interface Appointment {
    
    Room getRoom();

    LocalDateTime getDateTime();

    Duration getDuration();

    Person getPatient();

    String getType();

    Collection<Person> getDoctors();

}
