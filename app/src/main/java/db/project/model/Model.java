package db.project.model;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface Model {

    Collection<Person> getPersons(Optional<String> name, Optional<String> surname);

    Optional<Person> getPerson(String CF);

    Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role);

    Optional<Person> getDoctor(String CF);

    Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode);

    Optional<Person> getPatient(String CF);

}