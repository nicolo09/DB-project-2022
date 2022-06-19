package db.project.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Report;
import db.project.model.Uo;

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

    Collection<ASL> getASL(Optional<String> name, Optional<String> city, Optional<String> way, Optional<String> number);

    Optional<Hospital> getHospital(Integer code);

    Collection<Hospital> getHospitals(Optional<String> name, Optional<String> city, Optional<String> way,
            Optional<String> number, Optional<ASL> asl);

    Collection<Uo> getUos(Optional<String> name, Optional<Boolean> freeSpace, Optional<Hospital> hospital);

    Optional<Uo> getUo(Hospital hospital, String name);

    Optional<ASL> getASL(Integer code);

}
