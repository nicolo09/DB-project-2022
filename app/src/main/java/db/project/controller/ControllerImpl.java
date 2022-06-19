package db.project.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Model;
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
    public Collection<Uo> getUos(Optional<String> name, Optional<Boolean> freeSpace) {
        return this.model.getUos(name, freeSpace);
    }

    @Override
    public Optional<Uo> getUo(Hospital hospital, String name) {
        return this.model.getUo(hospital, name);
    }

}
