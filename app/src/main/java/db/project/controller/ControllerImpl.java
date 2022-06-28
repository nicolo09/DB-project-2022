package db.project.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.ASL;
import db.project.model.Appointment;
import db.project.model.Cure;
import db.project.model.Hospital;
import db.project.model.Model;
import db.project.model.OPERATION_OUTCOME;
import db.project.model.Person;
import db.project.model.Report;
import db.project.model.Uo;
import javafx.util.Pair;

public class ControllerImpl implements Controller {

    private Model model;
    
    public ControllerImpl(Model model) {
        this.model = model;
    }
    
    @Override
    public Collection<Person> getPersons(Optional<String> name, Optional<String> surname){
        return this.model.getPersons(name, surname);
    }
    
    @Override
    public Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role){
        return this.model.getDoctors(name, surname, role);
    }
    
    @Override
    public Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode){
        return this.model.getPatients(name, surname, birthDate, ASLCode);
    }
    
    @Override
    public Collection<Report> getRefertiByDoctor(Person doctor) {
        return this.model.getReportsFromDoctor(doctor);
    }

    @Override
    public Collection<Report> getRefertiByPatient(Person patient) {
        return this.model.getReportsFromPatient(patient);
    }

    @Override
    public Optional<Person> getDoctorByCF(String CF) {
        return model.getDoctor(CF);
    }

    @Override
    public Optional<Person> getPatientByCF(String CF) {
        return model.getPatient(CF);
    }

    @Override
    public Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role,
            Optional<Integer> HospitalCode) {
        return this.model.getManagers(name, surname, role, HospitalCode);
    }

    @Override
    public Collection<ASL> getASL(Optional<String> name, Optional<String> city, Optional<String> way,
            Optional<String> number) {
        return this.model.getASL(name, city, way, number);
    }
    
    @Override
    public Collection<Hospital> getHospitals(Optional<String> name, Optional<String> city, Optional<String> way,
    Optional<String> number, Optional<ASL> asl) {
        return this.model.getHospitals(name, city, way, number, asl);
    }
    
    @Override
    public Optional<Uo> getUo(Hospital hospital, String name) {
        return this.model.getUo(hospital, name);
    }
    
    @Override
    public Optional<Hospital> getHospital(Integer code) {
        return this.model.getHospital(code);
    }
    
    @Override
    public Collection<Uo> getUos(Optional<String> name, Optional<Boolean> freeSpace, Optional<Hospital> hospital) {
        return this.model.getUos(name, freeSpace, hospital);
    }
    
    @Override
    public Optional<ASL> getASL(Integer code) {
        return model.getASL(code);
    }
    
    @Override
    public Collection<Appointment> getAppointments(Optional<Person> doctor, Optional<Person> patient,
    Optional<Hospital> hospital, Optional<LocalDate> date) {
        return this.model.getAppointments(doctor, patient, hospital, date);
    }
    
    @Override
    public Collection<Cure> getCures(Optional<Person> patient, Optional<Uo> uo,
            Optional<Pair<LocalDate, LocalDate>> dateInInterval, Optional<Pair<LocalDate, LocalDate>> dateOutInterval,
            Optional<String> reason) {
        return this.model.getCures(patient, uo, dateInInterval, dateOutInterval, reason);
    }

    @Override
	public OPERATION_OUTCOME insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name,
    Optional<String> lastName) {
        return this.model.insertAmministratives(CF, role, hospitalCode, name, lastName);
	}
    
	@Override
	public OPERATION_OUTCOME insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration, String type,
    String patientCF, Collection<String> doctorCF) {
        return this.model.insertAppointment(hospitalCode, roomNumber, date, duration, type, patientCF, doctorCF);
	}
    
	@Override
	public OPERATION_OUTCOME insertASL(String name, String city, String street, int streetNumber) {
        return this.model.insertASL(name, city, street, streetNumber);
	}

	@Override
	public OPERATION_OUTCOME insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
			Optional<Date> exitDate, String description) {
		return this.model.insertCure(patientCF, hospitalCode, unitName, ingressDate, exitDate, description);
	}

	@Override
	public OPERATION_OUTCOME insertEquipment(int hospitalCode, String name, Date lastMaintenance) {
		return this.model.insertEquipment(hospitalCode, name, lastMaintenance);
	}

	@Override
	public OPERATION_OUTCOME insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName) {
		return this.model.insertHealtcare(CF, role, name, lastName);
	}

	@Override
	public OPERATION_OUTCOME insertHospital(String name, String city, String street, int streetNumber, int codeASL) {
		return this.model.insertHospital(name, city, street, streetNumber, codeASL);
	}

	@Override
	public OPERATION_OUTCOME insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name,
			Optional<String> lastName) {
		return this.model.insertPatient(CF, birthDay, codASL, name, lastName);
	}

	@Override
	public OPERATION_OUTCOME insertPerson(String CF, String name, String lastName) {
		return this.model.insertPerson(CF, name, lastName);
	}

	@Override
	public OPERATION_OUTCOME insertPhone(String phoneNumber, String personCF) {
		return this.model.insertPhone(phoneNumber, personCF);
	}

	@Override
	public OPERATION_OUTCOME insertReport(Date emissionDate, String description, String type, Optional<String> therapy,
			Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, int hospitalCode,
			String patientCF, Collection<String> doctorCF) {
		return this.model.insertReport(emissionDate, description, type, therapy, procedure, outcome, duration, hospitalCode, patientCF, doctorCF);
	}
    
	@Override
	public OPERATION_OUTCOME insertRoom(int hospitalCode, int roomNumber) {
		return this.model.insertRoom(hospitalCode, roomNumber);
	}

	@Override
	public OPERATION_OUTCOME insertUO(int hospitalCode, String name, int capacity, int seatsOccupied) {
		return this.model.insertUO(hospitalCode, name, capacity, seatsOccupied);
	}

	@Override
	public OPERATION_OUTCOME insertWorking(String CF, String unitName, int hospitalCode) {
		return this.model.insertWorking(CF, unitName, hospitalCode);
	}

    @Override
    public void close() {
        this.model.close();
    }
    
	@Override
	public OPERATION_OUTCOME updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode) {
		return this.model.updateAmministratives(CF, role, hospitalCode);
	}

	@Override
	public OPERATION_OUTCOME updateASL(int codeASL, Optional<String> name, Optional<String> city,
			Optional<String> street, Optional<Integer> streetNumber) {
		return this.model.updateASL(codeASL, name, city, street, streetNumber);
	}

	@Override
	public OPERATION_OUTCOME updateCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
			Optional<Date> exitDate, Optional<String> description) {
		return this.model.updateCure(patientCF, hospitalCode, unitName, ingressDate, exitDate, description);
	}

	@Override
	public OPERATION_OUTCOME updateEquipment(int hospitalCode, int inventoryCode, Optional<Date> lastMaintenance) {
		return this.model.updateEquipment(hospitalCode, inventoryCode, lastMaintenance);
	}

	@Override
	public OPERATION_OUTCOME updateHealtcare(String CF, Optional<String> role) {
		return this.model.updateHealtcare(CF, role);
	}

	@Override
	public OPERATION_OUTCOME updateHospital(int structureCode, Optional<String> name, Optional<Integer> codeASL) {
		return this.model.updateHospital(structureCode, name, codeASL);
	}

	@Override
	public OPERATION_OUTCOME updatePatient(String CF, Optional<Integer> codASL) {
		return this.model.updatePatient(CF, codASL);
	}

	@Override
	public OPERATION_OUTCOME updateUO(int hospitalCode, String name, Optional<Integer> capacity) {
		return this.model.updateUO(hospitalCode, name, capacity);
	}

	@Override
	public OPERATION_OUTCOME removeAmministratives(String CF) {
		return this.model.removeAmministratives(CF);
	}

	@Override
	public OPERATION_OUTCOME removeAppointment(int hospitalCode, int roomNumber, Timestamp date) {
		return this.model.removeAppointment(hospitalCode, roomNumber, date);
	}

	@Override
	public OPERATION_OUTCOME removeASL(int codeASL) {
		return this.model.removeASL(codeASL);
	}

	@Override
	public OPERATION_OUTCOME removeCure(String patientCF, int hospitalCode, String unitName, Date ingressDate) {
		return this.model.removeCure(patientCF, hospitalCode, unitName, ingressDate);
	}

	@Override
	public OPERATION_OUTCOME removeEquipment(int hospitalCode, int inventoryCode) {
		return this.model.removeEquipment(hospitalCode, inventoryCode);
	}

	@Override
	public OPERATION_OUTCOME removeHealtcare(String CF) {
		return this.model.removeHealtcare(CF);
	}

	@Override
	public OPERATION_OUTCOME removeHospital(int structureCode) {
		return this.model.removeHospital(structureCode);
	}

	@Override
	public OPERATION_OUTCOME removePatient(String CF) {
		return this.model.removePatient(CF);
	}

	@Override
	public OPERATION_OUTCOME removePerson(String CF) {
		return this.model.removePerson(CF);
	}

	@Override
	public OPERATION_OUTCOME removePhone(String phoneNumber, String personCF) {
		return this.model.removePhone(phoneNumber, personCF);
	}

	@Override
	public OPERATION_OUTCOME removeReport(int reportCode) {
		return this.model.removeReport(reportCode);
	}

	@Override
	public OPERATION_OUTCOME removeRoom(int hospitalCode, int roomNumber) {
		return this.model.removeRoom(hospitalCode, roomNumber);
	}

	@Override
	public OPERATION_OUTCOME removeUO(int hospitalCode, String name) {
		return this.model.removeUO(hospitalCode, name);
	}

	@Override
	public OPERATION_OUTCOME removeWorking(String CF, String unitName, int hospitalCode) {
		return this.model.removeWorking(CF, unitName, hospitalCode);
	}

    @Override
    public Collection<Pair<Person, String>> getTelephones(Person person) {
        return this.model.getTelephones(person);
    }

	@Override
	public void setHospital(int hospitalCode) {
		this.model.setHospital(hospitalCode);
	}

	@Override
	public int countDeletedEquipments() {
		return this.model.countDeletedEquipments();
	}

	@Override
	public int countDeletedAmministratives() {
		return this.model.countDeletedAmministratives();
	}

	@Override
	public int countDeletedReports() {
		return this.model.countDeletedReports();
	}

	@Override
	public int countDeletedRooms() {
		return this.model.countDeletedRooms();
	}

	@Override
	public int countDeletedAppointments() {
		return this.model.countDeletedAppointments();
	}

	@Override
	public int countDeletedUOs() {
		return this.model.countDeletedUOs();
	}

	@Override
	public int countDeletedCures() {
		return this.model.countDeletedCures();
	}

	@Override
	public int countDeletedJobs() {
		return this.model.countDeletedJobs();
	}

}
