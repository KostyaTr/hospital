package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Appointment;
import com.github.KostyaTr.hospital.model.GuestPatient;

import java.util.List;

public interface GuestPatientDao {
    List<GuestPatient> getPatientsByDoctorId(Long doctorId);

    List<GuestPatient> getPatientsByDepartmentId(Long deptId);

    List<GuestPatient> getPatients();

    Long addPatient(Appointment appointment);

    boolean removePatientById(Long patientId);

    GuestPatient getPatientById(Long patientId);
}
