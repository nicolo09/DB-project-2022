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
