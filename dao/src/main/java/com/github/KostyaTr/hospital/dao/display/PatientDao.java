package com.github.KostyaTr.hospital.dao.display;

import com.github.KostyaTr.hospital.model.display.Patient;

import java.util.List;

public interface PatientDao {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    Patient getPatientById(Long patientId);
}
