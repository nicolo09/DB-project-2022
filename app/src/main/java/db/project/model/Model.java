package db.project.model;

import java.sql.Timestamp;
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

    Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role,
            Optional<Integer> hospitalCode);
    
    boolean insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name, Optional<String> lastName);
    
    boolean insertAppointment(int hospitalCode, int roomNumber, Timestamp date,
    		int duration, String type, String patientCF, Collection<String> doctorCF);
    
    boolean insertASL(int codeASL, String name, String city, String street, int streetNumber);
    
    boolean insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
    		Optional<Date> exitDate, String description);
    
    boolean insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance);
    
    boolean insertHealtcare(String CF, String role, Optional<String> unitName, Optional<Integer> hospitalCode);
    
    boolean insertHospital(int structureCode, String name, String city, String street, String streetNumber);
    
    boolean insertPatient(String CF, Date birthDay, Optional<Integer> codASL);
    
    boolean insertPerson(String CF, String name, String lastName);
    
    boolean insertPhone(String phoneNumber, String personCF);
    
    boolean insertReport(int reportCode, Date emissionDate, String description, String type, 
    		Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, 
    		int hospitalCode, String patientCF, Collection<String> doctorCF);
    
    boolean insertRoom(int hospitalCode, int roomNumber);
    
    boolean insertUO(int hospitalCode, String name, int capacity, int seatsOccupied);

}