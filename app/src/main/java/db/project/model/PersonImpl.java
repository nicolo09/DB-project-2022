package db.project.model;

public class PersonImpl implements Person {


    private final String name;
    private final String surname;
    private final String CF;
        
    public PersonImpl(String name, String surname, String cF) {
        this.name = name;
        this.surname = surname;
        this.CF = cF;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getCF() {
        return this.CF;
    }

}
