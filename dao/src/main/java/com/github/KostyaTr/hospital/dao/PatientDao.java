package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface PatientDao {
    List<Patient> getPatientsByDoctor(String doctorName);

    List<Patient> getPatients();

    void addPatient(Patient patient);

    boolean removePatient(String patientName);

    List<Patient> getPatientsByLogin(String login);
}
