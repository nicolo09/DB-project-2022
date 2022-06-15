package db.project.controller;

import java.util.List;
import java.util.Optional;

import db.project.model.Doctor;
import db.project.model.Patient;
import db.project.model.Report;

public interface Controller {
    
    List<Report> getRefertiByDoctor(Doctor doctor);
    
    List<Report> getRefertiByPatient(Patient patient);
    
    Optional<Doctor> getDoctorByCF(String CF);

}
