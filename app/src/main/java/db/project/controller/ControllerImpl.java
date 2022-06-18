package db.project.controller;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.project.model.Model;
import db.project.model.Person;
import db.project.model.Report;

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
    public List<Report> getRefertiByDoctor(Person doctor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Report> getRefertiByPatient(Person patient) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Person> getDoctorByCF(String CF) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role,
            Optional<Integer> HospitalCode) {
        return this.model.getManagers(name, surname, role, HospitalCode);
    }

	@Override
	public boolean insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name,
			Optional<String> lastName) {
		return this.model.insertAmministratives(CF, role, hospitalCode, name, lastName);
	}

	@Override
	public boolean insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration, String type,
			String patientCF, Collection<String> doctorCF) {
		return this.model.insertAppointment(hospitalCode, roomNumber, date, duration, type, patientCF, doctorCF);
	}

	@Override
	public boolean insertASL(String name, String city, String street, int streetNumber) {
		return this.model.insertASL(name, city, street, streetNumber);
	}

	@Override
	public boolean insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
			Optional<Date> exitDate, String description) {
		return this.model.insertCure(patientCF, hospitalCode, unitName, ingressDate, exitDate, description);
	}

	@Override
	public boolean insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance) {
		return this.model.insertEquipment(hospitalCode, inventoryCode, name, lastMaintenance);
	}

	@Override
	public boolean insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName) {
		return this.model.insertHealtcare(CF, role, name, lastName);
	}

	@Override
	public boolean insertHospital(String name, String city, String street, int streetNumber, int codeASL) {
		return this.model.insertHospital(name, city, street, streetNumber, codeASL);
	}

	@Override
	public boolean insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name,
			Optional<String> lastName) {
		return this.model.insertPatient(CF, birthDay, codASL, name, lastName);
	}

	@Override
	public boolean insertPerson(String CF, String name, String lastName) {
		return this.model.insertPerson(CF, name, lastName);
	}

	@Override
	public boolean insertPhone(String phoneNumber, String personCF) {
		return this.model.insertPhone(phoneNumber, personCF);
	}

	@Override
	public boolean insertReport(Date emissionDate, String description, String type, Optional<String> therapy,
			Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, int hospitalCode,
			String patientCF, Collection<String> doctorCF) {
		return this.model.insertReport(emissionDate, description, type, therapy, procedure, outcome, duration, hospitalCode, patientCF, doctorCF);
	}

	@Override
	public boolean insertRoom(int hospitalCode, int roomNumber) {
		return this.model.insertRoom(hospitalCode, roomNumber);
	}

	@Override
	public boolean insertUO(int hospitalCode, String name, int capacity, int seatsOccupied) {
		return this.model.insertUO(hospitalCode, name, capacity, seatsOccupied);
	}

	@Override
	public boolean insertWorking(String CF, String unitName, int hospitalCode) {
		return this.model.insertWorking(CF, unitName, hospitalCode);
	}

}
