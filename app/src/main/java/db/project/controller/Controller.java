package db.project.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.project.model.Doctor;
import db.project.model.Patient;
import db.project.model.Person;
import db.project.model.Report;

public interface Controller {
    
    List<Report> getRefertiByDoctor(Doctor doctor);
    
    List<Report> getRefertiByPatient(Patient patient);
    
    Optional<Doctor> getDoctorByCF(String CF);

    Collection<Person> getPersons(Optional<String> name, Optional<String> surname);

    Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role);

    Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode);

}
