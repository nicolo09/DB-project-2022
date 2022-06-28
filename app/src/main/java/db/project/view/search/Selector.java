package db.project.view.search;

import db.project.model.ASL;
import db.project.model.Appointment;
import db.project.model.Cure;
import db.project.model.Equipment;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Report;
import db.project.model.Room;
import db.project.model.Uo;
import javafx.util.Pair;

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

    /**
     * 
     * @return the selected {@link Equipment}.
     */
    public Equipment selectEquipment();

    /**
     * 
     * @return the selected {@link Administrative}.
     */
    public Person selectAdministrative();

    /**
     * 
     * @return the selected TODO.
     */
    public Person selectPatient();

    /**
     * 
     * @return the selected {@link Doctor}.
     */
    public Person selectDoctor();

    /**
     * 
     * @return the selected {@link Room}.
     */
    public Room selectRoom();

    /**
     * 
     * @return the selected {@link Appointment}.
     */
    public Appointment selectAppointment();

    /**
     * 
     * @return the selected {@link Cure}.
     */
    public Cure selectCure();

    /**
     * 
     * @return the selected {@link Report}.
     */
    public Report selectReport();


    /**
     * @return the selected telephone as {@link Pair} (key = person, value = telephone number).
     */
    public Pair<Person, String> selectPhone();

    /**
     * @return the selected job as {@link Pair} (key = U.O., value = Person).
     */
    public Pair<Uo, Person> selectJob();
}
