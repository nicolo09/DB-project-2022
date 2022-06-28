package db.project.model.mysql;

import java.sql.Timestamp;
import java.util.Date;

import db.project.model.OPERATION_OUTCOME;

public interface DataRemover {
	
	OPERATION_OUTCOME removeAmministratives(String CF);
    
    OPERATION_OUTCOME removeAppointment(int hospitalCode, int roomNumber, Timestamp date);
    
    OPERATION_OUTCOME removeASL(int codeASL);
    
    OPERATION_OUTCOME removeCure(String patientCF, int hospitalCode, String unitName, Date ingressDate);
    
    OPERATION_OUTCOME removeEquipment(int hospitalCode, int inventoryCode);
    
    OPERATION_OUTCOME removeHealtcare(String CF);
    
    OPERATION_OUTCOME removeHospital(int structureCode);
    
    OPERATION_OUTCOME removePatient(String CF);
    
    OPERATION_OUTCOME removePerson(String CF);
    
    OPERATION_OUTCOME removePhone(String phoneNumber, String personCF);
    
    OPERATION_OUTCOME removeReport(int reportCode);
    
    OPERATION_OUTCOME removeRoom(int hospitalCode, int roomNumber);
    
    OPERATION_OUTCOME removeUO(int hospitalCode, String name);
    
    OPERATION_OUTCOME removeWorking(String CF, String unitName, int hospitalCode);

}
