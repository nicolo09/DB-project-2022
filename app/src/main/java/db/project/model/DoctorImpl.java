package db.project.model;

public class DoctorImpl extends PersonImpl {

    private final String role;

    public DoctorImpl(String name, String surname, String CF, String role) {
        super(name, surname, CF);
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
    
}
