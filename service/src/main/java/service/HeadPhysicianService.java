package service;

import model.MedDoctor;
import model.Patient;

import java.util.List;

public interface HeadPhysicianService extends MedDoctorService {
    boolean fireDoctor(MedDoctor medDoctor);
}
