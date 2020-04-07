package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface MedDoctorService {
    List<Patient> getPatientsByDoctor(String doctorName);

    List<Patient> getPatients();

    boolean curePatient(String patientName);
}
