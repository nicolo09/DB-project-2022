package db.project.model;

public class AdministrativeImpl extends PersonImpl {

    private final String role;
    private final Hospital hospital;

    public AdministrativeImpl(String name, String surname, String CF, String role, Hospital hospital) {
        super(name, surname, CF);
        this.role = role;
        this.hospital = hospital;
    }

    public String getRole() {
        return this.role;
    }
    
    public Hospital getHospital() {
        return this.hospital;
    }
}
