package db.project.controller;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.ASL;
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
    
 boolean insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name, Optional<String> lastName);
    
    boolean insertAppointment(int hospitalCode, int roomNumber, Timestamp date,
    		int duration, String type, String patientCF, Collection<String> doctorCF);
    
    boolean insertASL(String name, String city, String street, int streetNumber);
    
    boolean insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
    		Optional<Date> exitDate, String description);
    
    boolean insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance);
    
    boolean insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName);
    
    boolean insertHospital(String name, String city, String street, int streetNumber, int codeASL);
    
    boolean insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name, Optional<String> lastName);
    
    boolean insertPerson(String CF, String name, String lastName);
    
    boolean insertPhone(String phoneNumber, String personCF);
    
    boolean insertReport(Date emissionDate, String description, String type, 
    		Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, 
    		int hospitalCode, String patientCF, Collection<String> doctorCF);
    
    boolean insertRoom(int hospitalCode, int roomNumber);
    
    boolean insertUO(int hospitalCode, String name, int capacity, int seatsOccupied);
    
    boolean insertWorking(String CF, String unitName, int hospitalCode);

        Collection<ASL> getASL(Optional<String> name, Optional<String> city, Optional<String> way,
                        Optional<String> number);

}
