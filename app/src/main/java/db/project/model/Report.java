package db.project.model;

import java.util.Date;

public interface Report {
    
    Integer getCode();
    
    Date getDate();
    
    String getDescription();

    Hospital getHospital();

    Person getPatient();

}
