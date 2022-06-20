package db.project.model;

public class RoomImpl implements Room {
    
    private final Hospital hospital;
    private final Integer roomNumber;

    public RoomImpl(final Hospital hospital, final Integer roomNumber) {
        this.hospital = hospital;
        this.roomNumber = roomNumber;
    }

    @Override
    public Hospital getHospital() {
        return hospital;
    }

    @Override
    public Integer getRoomNumber() {
        return roomNumber;
    }
}
