package db.project.model;

import java.time.LocalDate;

public class EquipmentImpl implements Equipment {

    private final int code;
    private final String name;
    private final LocalDate lastMaintenance;
    private final Hospital hospital;

    public EquipmentImpl(final int code, final String name, final LocalDate lastMaintenance, final Hospital hospital) {
        this.code = code;
        this.name = name;
        this.lastMaintenance = lastMaintenance;
        this.hospital = hospital;
    }

    @Override
    public Hospital getHospital() {
        return hospital;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }


    
}
