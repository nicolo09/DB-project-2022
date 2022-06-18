package db.project.model.mysql;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public interface DataUpdater {
	
	boolean updateAmministratives(String CF, Optional<String> Role, Optional<Integer> hospitalCode);
    
    boolean updateAppointment(int hospitalCode, int roomNumber, Timestamp date, Optional<Integer> duration, 
    		Optional<String> type, Optional<String> patientCF);//, Optional<Collection<String>> doctorCF);
    
    boolean updateASL(int codeASL, Optional<String> name, Optional<String> city, Optional<String> street, Optional<Integer> streetNumber);
    
    boolean updateCure(String patientCF, int hospitalCode, String unitName, Optional<Date> ingressDate,
    		Optional<Date> exitDate, Optional<String> description);
    
    boolean updateEquipment(int hospitalCode, int inventoryCode, Optional<String> name, Optional<Date> lastMaintenance);
    
    boolean updateHealtcare(String CF, Optional<String> role);
    
    boolean updateHospital(int structureCode, Optional<String> name, Optional<String> city, Optional<String> street,
    		Optional<Integer> streetNumber, Optional<Integer> codeASL);
    
    boolean updatePatient(String CF, Optional<Date> birthDay, Optional<Integer> codASL);
    
    //boolean updatePerson(String CF, String name, String lastName);
    
    //boolean updatePhone(String phoneNumber, String personCF);
    
    /*boolean updateReport(Date emissionDate, String description, String type, 
    		Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, 
    		int hospitalCode, String patientCF, Collection<String> doctorCF);*/
    
    //boolean updateRoom(int hospitalCode, int roomNumber);
    
    boolean updateUO(int hospitalCode, String name, Optional<Integer> capacity, Optional<Integer> seatsOccupied);
    
    //boolean updateWorking(String CF, String unitName, int hospitalCode);

}
