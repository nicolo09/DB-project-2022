package db.project.model;

public interface Uo {
    
    Hospital getHospital();

    String getName();

    Integer getCapacity();

    Integer getOccupiedPlaces();

    Boolean hasFreeSpace();

}
