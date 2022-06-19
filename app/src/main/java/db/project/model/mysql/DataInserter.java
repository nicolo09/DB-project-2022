package db.project.model.mysql;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.OPERATION_OUTCOME;

public interface DataInserter {
	
	OPERATION_OUTCOME insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name, Optional<String> lastName);
    
    OPERATION_OUTCOME insertAppointment(int hospitalCode, int roomNumber, Timestamp date,
    		int duration, String type, String patientCF, Collection<String> doctorCF);
    
    OPERATION_OUTCOME insertASL(String name, String city, String street, int streetNumber);
    
    OPERATION_OUTCOME insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
    		Optional<Date> exitDate, String description);
    
    OPERATION_OUTCOME insertEquipment(int hospitalCode, String name, Date lastMaintenance);
    
    OPERATION_OUTCOME insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName);
    
    OPERATION_OUTCOME insertHospital(String name, String city, String street, int streetNumber, int codeASL);
    
    OPERATION_OUTCOME insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name, Optional<String> lastName);
    
    OPERATION_OUTCOME insertPerson(String CF, String name, String lastName);
    
    OPERATION_OUTCOME insertPhone(String phoneNumber, String personCF);
    
    OPERATION_OUTCOME insertReport(Date emissionDate, String description, String type, 
    		Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, 
    		int hospitalCode, String patientCF, Collection<String> doctorCF);
    
    OPERATION_OUTCOME insertRoom(int hospitalCode, int roomNumber);
    
    OPERATION_OUTCOME insertUO(int hospitalCode, String name, int capacity, int seatsOccupied);
    
    OPERATION_OUTCOME insertWorking(String CF, String unitName, int hospitalCode);

}
