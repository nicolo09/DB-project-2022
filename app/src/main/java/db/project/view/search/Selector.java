package db.project.view.search;

import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Uo;

public interface Selector {

    public Person selectPerson();

    public Hospital selectHospital();

    public ASL selectAsl();

    public Uo selectUo();
    
}
