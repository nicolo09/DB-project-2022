package db.project.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.project.model.Doctor;
import db.project.model.Model;
import db.project.model.Patient;
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
    public List<Report> getRefertiByDoctor(Doctor doctor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Report> getRefertiByPatient(Patient patient) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Doctor> getDoctorByCF(String CF) {
        // TODO Auto-generated method stub
        return null;
    }

}
