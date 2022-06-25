package db.project.view.search;

import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Uo;

/**
 * This interface models an object used to select an entity.
 */
public interface Selector {
    /**
     * 
     * @return the selected {@link Person}.
     */
    public Person selectPerson();

    /**
     * 
     * @return the selected {@link Hospital}.
     */
    public Hospital selectHospital();

    /**
     * 
     * @return the selected {@link ASL}.
     */
    public ASL selectAsl();

    /**
     * 
     * @return the selected {@link Uo}.
     */
    public Uo selectUo();

}
