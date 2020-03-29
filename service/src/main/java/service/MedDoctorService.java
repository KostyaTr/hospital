package service;

import model.Patient;

import java.util.List;

public interface MedDoctorService {
    List<Patient> getPatientsByDoctor(String doctorName);

    List<Patient> getPatients();

    boolean curePatient(String patientName);
}
