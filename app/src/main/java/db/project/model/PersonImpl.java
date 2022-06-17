package db.project.model;

import java.util.Objects;

public class PersonImpl implements Person {


    private final String name;
    private final String surname;
    private final String CF;
        
    public PersonImpl(String name, String surname, String CF) {
        this.name = name;
        this.surname = surname;
        this.CF = CF;
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

    @Override
    public int hashCode() {
        return Objects.hash(CF, name, surname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonImpl other = (PersonImpl) obj;
        return Objects.equals(this.CF, other.CF) && Objects.equals(this.name, other.name)
                && Objects.equals(this.surname, other.surname);
    }

}
