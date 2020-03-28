package dao;

import model.Patient;

import java.util.List;

public interface PatientDao {
    List<Patient> getPatientsByDoctor(String doctorName);

    List<Patient> getPatients();

    void addPatient(Patient patient);

    void removePatient(String patientName);

    List<Patient> getPatientsByLogin(String login);
}
