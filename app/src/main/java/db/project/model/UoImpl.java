package db.project.model;

public class UoImpl implements Uo {

    private Hospital hospital;
    private String name;
    private Integer capacity;
    private Integer occupiedPlaces;

    public UoImpl(final Hospital hospital, final String name, final Integer capacity, final Integer occupiedPlaces) {
        this.hospital = hospital;
        this.name = name;
        this.capacity = capacity;
        this.occupiedPlaces = occupiedPlaces;
    }

    @Override
    public Hospital getHospital() {
        return this.hospital;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getCapacity() {
        return this.capacity;
    }

    @Override
    public Integer getOccupiedPlaces() {
        return this.occupiedPlaces;
    }

    @Override
    public Boolean hasFreeSpace() {
        return this.capacity > this.occupiedPlaces;
    }
    
}
