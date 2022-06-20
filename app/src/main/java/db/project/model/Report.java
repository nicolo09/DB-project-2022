package db.project.model;

import java.util.Collection;
import java.util.Date;

public interface Report {
    
    Integer getCode();
    
    Date getDate();
    
    String getDescription();

    Hospital getHospital();

    Person getPatient();

    Collection<Person> getInvolvedDoctors();

}
