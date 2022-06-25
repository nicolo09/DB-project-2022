package db.project.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class AppointmentImpl implements Appointment {

    private final Room room;
    private final LocalDateTime dateTime;
    private final Duration duration;
    private final Person patient;
    private final String type;
    private final Collection<Person> doctors;

    public AppointmentImpl(final Room room, final LocalDateTime dateTime, final Duration duration, final Person patient,
            final String type, final Collection<Person> doctors) {
        this.room = room;
        this.dateTime = dateTime;
        this.duration = duration;
        this.patient = patient;
        this.type = type;
        this.doctors = Set.copyOf(doctors);
        Set.of(patient);
    }

    public AppointmentImpl(final Room room, final LocalDateTime dateTime, final Duration duration, final Person patient,
            final String type, final Person...doctors) {
        this(room, dateTime, duration, patient, type, Set.of(doctors));
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Person getPatient() {
        return patient;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Collection<Person> getDoctors() {
        return Set.copyOf(doctors);
    }
}
