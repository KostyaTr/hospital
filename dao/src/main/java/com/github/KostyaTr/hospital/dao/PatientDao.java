package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Patient;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    List<Patient> getPatients();

    Long addPatient(Patient patient);

    boolean removePatientById(Long patientId);

    Patient getPatientById(Long patientId);

    int getLatestCouponToDoctorByDay(Long doctorId, int day);

    LocalDateTime getLatestTimeToDoctorByDay(Long doctorId, int day);

    boolean updateVisitDate(Patient patient);
}
