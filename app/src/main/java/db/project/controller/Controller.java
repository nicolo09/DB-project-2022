package db.project.controller;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.ASL;
import db.project.model.OPERATION_OUTCOME;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Report;
import db.project.model.Uo;

public interface Controller {

    void close();

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

    OPERATION_OUTCOME insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name,
            Optional<String> lastName);

    OPERATION_OUTCOME insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration, String type,
            String patientCF, Collection<String> doctorCF);

    OPERATION_OUTCOME insertASL(String name, String city, String street, int streetNumber);

    OPERATION_OUTCOME insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
            Optional<Date> exitDate, String description);

    OPERATION_OUTCOME insertEquipment(int hospitalCode, String name, Date lastMaintenance);

    OPERATION_OUTCOME insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName);

    OPERATION_OUTCOME insertHospital(String name, String city, String street, int streetNumber, int codeASL);

    OPERATION_OUTCOME insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name,
            Optional<String> lastName);

    OPERATION_OUTCOME insertPerson(String CF, String name, String lastName);

    OPERATION_OUTCOME insertPhone(String phoneNumber, String personCF);

    OPERATION_OUTCOME insertReport(Date emissionDate, String description, String type, Optional<String> therapy,
            Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, int hospitalCode,
            String patientCF, Collection<String> doctorCF);

    OPERATION_OUTCOME insertRoom(int hospitalCode, int roomNumber);

    OPERATION_OUTCOME insertUO(int hospitalCode, String name, int capacity, int seatsOccupied);

    OPERATION_OUTCOME insertWorking(String CF, String unitName, int hospitalCode);
}
