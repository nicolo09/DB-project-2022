package db.project.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.Person;
import db.project.model.Report;

public interface Controller {
    
    Collection<Report> getRefertiByDoctor(Person doctor);
    
    Collection<Report> getRefertiByPatient(Person patient);
    
    Optional<Person> getDoctorByCF(String CF);

    Optional<Person> getPatientByCF(String text);

    Collection<Person> getPersons(Optional<String> name, Optional<String> surname);

    Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role);

    Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode);
    
    Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role, 
            Optional<Integer> HospitalCode);

}
