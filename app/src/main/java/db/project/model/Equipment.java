package db.project.model;

import java.time.LocalDate;

public interface Equipment {

    public Hospital getHospital();

    public Integer getCode();

    public String getName();

    public LocalDate getMaintenanceDate(); 

}
