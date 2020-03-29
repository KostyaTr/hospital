package service;

import model.MedDoctor;
import model.Patient;

import java.util.List;

public interface HeadPhysicianService {
    void fireDoctor(MedDoctor medDoctor);

    List<Patient> getPatients();
}
