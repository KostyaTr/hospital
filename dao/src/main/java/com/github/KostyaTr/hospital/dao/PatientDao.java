package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Appointment;
import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface PatientDao {
    List<Patient> getPatientsByDoctorId(Long doctorId);

    List<Patient> getPatientsByDepartmentId(Long deptId);

    List<Patient> getPatients();

    Long addPatient(Appointment appointment);

    boolean removePatientById(Long patientId);

    Patient getPatientById(Long patientId);
}
