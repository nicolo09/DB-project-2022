package db.project.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javafx.util.Pair;

public interface Model {

    void close();

    /**
     * 
     * @param name
     * @param surname
     * @return all the persons associated with the given name and surname
     */
    Collection<Person> getPersons(Optional<String> name, Optional<String> surname);

    /**
     * 
     * @param name
     * @param surname
     * @return the persons associated with the given name and surname (or
     *         Optional.empty() if no such person exists)
     */
    Optional<Person> getPerson(String CF);

    /**
     * 
     * @param name
     * @param surname
     * @param role
     * @return all the doctors associated with the given name and surname and role
     */
    Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role);

    Optional<Person> getDoctor(String CF);

    Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode);

    Optional<Person> getPatient(String CF);

    Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role,
            Optional<Integer> hospitalCode);

    Collection<Report> getReportsFromPatient(Person patient);

    Collection<Report> getReportsFromDoctor(Person doctor);

    /**
     * 
     * @param code
     * @return the hospital associated with the given code
     */
    Optional<Hospital> getHospital(Integer code);

    Collection<Hospital> getHospitals(Optional<String> name, Optional<String> city, Optional<String> way,
            Optional<String> number, Optional<ASL> asl);

    Collection<ASL> getASL(Optional<String> name, Optional<String> city, Optional<String> way, Optional<String> number);

    /**
     * 
     * @param code
     * @return the asl associated with the given code
     */
    Optional<ASL> getASL(final Integer code);

    /**
     * 
     * @param hospital
     * @param name
     * @return the UO associated with the given hospital and name
     */
    Optional<Uo> getUo(Hospital hospital, String name);

    /**
     * @param name      name of the Uo
     * @param freeSpace true if show only Uo with free space, false show only full
     *                  Uos
     * @param hospital
     * @return the collection of all UOs filtered by the given name or free space
     *         (or both)
     */
    Collection<Uo> getUos(Optional<String> name, Optional<Boolean> freeSpace, Optional<Hospital> hospital);

    Collection<Appointment> getAppointments(Optional<Person> doctor, Optional<Person> patient,
            Optional<Hospital> hospital, Optional<LocalDate> date);

    Collection<Cure> getCures(Optional<Person> patient, Optional<Uo> uo,
            Optional<Pair<LocalDate, LocalDate>> dateInInterval, Optional<Pair<LocalDate, LocalDate>> dateOutInterval,
            Optional<String> reason);

    OPERATION_OUTCOME insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name,
            Optional<String> lastName);

    OPERATION_OUTCOME insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration, String type,
            String patientCF, Collection<String> doctorCF);

    OPERATION_OUTCOME insertASL(String name, String city, String street, int streetNumber);

    OPERATION_OUTCOME insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
            Optional<Date> exitDate, String description);

    OPERATION_OUTCOME insertEquipment(int hospitalCode, String name, Date lastMaintenance);

    OPERATION_OUTCOME insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName);

    OPERATION_OUTCOME insertHospital(String name, String city, String street, int streetNumber, int codeASL);

    OPERATION_OUTCOME insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name,
            Optional<String> lastName);

    OPERATION_OUTCOME insertPerson(String CF, String name, String lastName);

    OPERATION_OUTCOME insertPhone(String phoneNumber, String personCF);

    OPERATION_OUTCOME insertReport(Date emissionDate, String description, String type, Optional<String> therapy,
            Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, int hospitalCode,
            String patientCF, Collection<String> doctorCF);

    OPERATION_OUTCOME insertRoom(int hospitalCode, int roomNumber);

    OPERATION_OUTCOME insertUO(int hospitalCode, String name, int capacity, int seatsOccupied);

    OPERATION_OUTCOME insertWorking(String CF, String unitName, int hospitalCode);

    OPERATION_OUTCOME updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode);
    
    OPERATION_OUTCOME updateASL(int codeASL, Optional<String> name, Optional<String> city, Optional<String> street, Optional<Integer> streetNumber);
    
    OPERATION_OUTCOME updateCure(String patientCF, int hospitalCode, String unitName, Date ingressDate, Optional<Date> exitDate, Optional<String> description);
    
    OPERATION_OUTCOME updateEquipment(int hospitalCode, int inventoryCode, Optional<Date> lastMaintenance);

    OPERATION_OUTCOME updateHealtcare(String CF, Optional<String> role);

    OPERATION_OUTCOME updateHospital(int structureCode, Optional<String> name, Optional<Integer> codeASL);

    OPERATION_OUTCOME updatePatient(String CF, Optional<Integer> codASL);

    OPERATION_OUTCOME updateUO(int hospitalCode, String name, Optional<Integer> capacity);

    OPERATION_OUTCOME removeAmministratives(String CF);

    OPERATION_OUTCOME removeAppointment(int hospitalCode, int roomNumber, Timestamp date);

    OPERATION_OUTCOME removeASL(int codeASL);
    
    OPERATION_OUTCOME removeCure(String patientCF, int hospitalCode, String unitName, Date ingressDate);
    
    OPERATION_OUTCOME removeEquipment(int hospitalCode, int inventoryCode);

    OPERATION_OUTCOME removeHealtcare(String CF);

    OPERATION_OUTCOME removeHospital(int structureCode);

    OPERATION_OUTCOME removePatient(String CF);

    OPERATION_OUTCOME removePerson(String CF);

    OPERATION_OUTCOME removePhone(String phoneNumber, String personCF);

    OPERATION_OUTCOME removeReport(int reportCode);

    OPERATION_OUTCOME removeRoom(int hospitalCode, int roomNumber);

    OPERATION_OUTCOME removeUO(int hospitalCode, String name);

    OPERATION_OUTCOME removeWorking(String CF, String unitName, int hospitalCode);
    
    void setHospital(int hospitalCode);
	
	int countDeletedEquipments();
	
	int countDeletedAmministratives();
	
	int countDeletedReports();
	
	int countDeletedRooms();
	
	int countDeletedAppointments();
	
	int countDeletedUOs();
	
	int countDeletedCures();
	
	int countDeletedJobs();

    Collection<Pair<Person, String>> getTelephones(Person person);

}