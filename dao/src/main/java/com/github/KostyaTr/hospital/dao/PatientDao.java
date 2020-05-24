package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Patient;

import java.util.Date;
import java.util.List;

public interface PatientDao {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    List<Patient> getPatients();

    Long addPatient(Patient patient);

    boolean removePatientById(Long patientId);

    void removePatientByUserId(Long userId);

    Patient getPatientById(Long patientId);

    List<Patient> getPatientsByUserId(Long userId);

    int getLatestCouponToDoctorByDay(Long doctorId, int day);

    Date getLatestTimeToDoctorByDay(Long doctorId, int day);

    void updateVisitDate(Patient patient);
}
