package db.project.model.mysql;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface DataInserter {
	
boolean insertAmministratives(String CF, String Role, int hospitalCode, Optional<String> name, Optional<String> lastName);
    
    boolean insertAppointment(int hospitalCode, int roomNumber, Timestamp date,
    		int duration, String type, String patientCF, Collection<String> doctorCF);
    
    boolean insertASL(int codeASL, String name, String city, String street, int streetNumber);
    
    boolean insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
    		Optional<Date> exitDate, String description);
    
    boolean insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance);
    
    boolean insertHealtcare(String CF, String Role, Optional<String> unitName, Optional<Integer> hospitalCode);
    
    boolean insertHospital(int structureCode, String name, String city, String street, String streetNumber);
    
    boolean insertPatient(String CF, Date birthDay, Optional<Integer> codASL);
    
    boolean insertPerson(String CF, String name, String lastName);
    
    boolean insertPhone(String phoneNumber, String personCF);
    
    boolean insertReport(Date emissionDate, String description, String type, 
    		Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, 
    		int hospitalCode, String patientCF, Collection<String> doctorCF);
    
    boolean insertRoom(int hospitalCode, int roomNumber);
    
    boolean insertUO(int hospitalCode, String name, int capacity, int seatsOccupied);

}
