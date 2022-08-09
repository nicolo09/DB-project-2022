package db.project.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import db.project.model.Model;
import db.project.model.ModelImpl;
import db.project.model.OPERATION_OUTCOME;
import db.project.model.Person;
import db.project.model.PersonImpl;

public class PersonsLoader extends AbstractLoader {

    private static final char FIELDDELIMITER = '|';
    private final Model model;

    public PersonsLoader(String csvPath, String username, String password) {
        super(csvPath, FIELDDELIMITER);
        model = new ModelImpl(username, password);
    }

    public void loadInDB() {
        Collection<Person> persons = new ArrayList<>();
        try {
            Collection<List<String>> entries = this.load();
            for (List<String> entry : entries) {
                persons.add(new PersonImpl(entry.get(1), entry.get(0), entry.get(2)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Person person : persons) {
            if (this.model.insertPerson(person.getCF(), person.getName(),
                    person.getSurname()) == OPERATION_OUTCOME.SUCCESS) {
                System.out.println(
                        "Person inserted: " + person.getName() + " " + person.getSurname() + " " + person.getCF());
            } else {
                System.out.println("ERROR: " + person.getName() + " " + person.getSurname() + " " + person.getCF());
            }
        }
        System.out.println("Persons loaded: " + persons.size());
    }

}
