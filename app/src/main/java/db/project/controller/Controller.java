package db.project.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.project.model.Person;
import db.project.model.Report;

public interface Controller {
    
    List<Report> getRefertiByDoctor(Person doctor);
    
    List<Report> getRefertiByPatient(Person patient);
    
    Optional<Person> getDoctorByCF(String CF);

    Collection<Person> getPersons(Optional<String> name, Optional<String> surname);

    Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role);

    Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode);
    
    Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role, 
            Optional<Integer> HospitalCode);

}
