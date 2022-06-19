package db.project.model.mysql;

import java.util.Date;
import java.util.Optional;

import db.project.model.OPERATION_OUTCOME;

public interface DataUpdater {
	
	OPERATION_OUTCOME updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode);
    
    OPERATION_OUTCOME updateASL(int codeASL, Optional<String> name, Optional<String> city, Optional<String> street, Optional<Integer> streetNumber);
    
    OPERATION_OUTCOME updateCure(String patientCF, int hospitalCode, String unitName, Optional<Date> exitDate, Optional<String> description);
    
    OPERATION_OUTCOME updateEquipment(int hospitalCode, int inventoryCode, Optional<Date> lastMaintenance);
    
    OPERATION_OUTCOME updateHealtcare(String CF, Optional<String> role);
    
    OPERATION_OUTCOME updateHospital(int structureCode, Optional<String> name, Optional<Integer> codeASL);
    
    OPERATION_OUTCOME updatePatient(String CF, Optional<Integer> codASL);
    
    //OPERATION_OUTCOME updatePerson(String CF, String name, String lastName);
    
    //OPERATION_OUTCOME updatePhone(String phoneNumber, String personCF);
    
    /*OPERATION_OUTCOME updateReport(Date emissionDate, String description, String type, 
    		Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, 
    		int hospitalCode, String patientCF, Collection<String> doctorCF);*/
    
    //OPERATION_OUTCOME updateRoom(int hospitalCode, int roomNumber);
    
    OPERATION_OUTCOME updateUO(int hospitalCode, String name, Optional<Integer> capacity, Optional<Integer> seatsOccupied);
    
    //OPERATION_OUTCOME updateWorking(String CF, String unitName, int hospitalCode);

}
