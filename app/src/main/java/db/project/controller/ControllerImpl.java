package db.project.controller;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Model;
import db.project.model.OPERATION_OUTCOME;
import db.project.model.Person;
import db.project.model.Report;
import db.project.model.Uo;

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
        return this.model.getReports(Optional.empty(), Optional.of(doctor));
    }

    @Override
    public Collection<Report> getRefertiByPatient(Person patient) {
        return this.model.getReports(Optional.of(patient), Optional.empty());
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

}
