package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Inpatient;

import java.util.List;

public interface InpatientDao {
    List<Inpatient> getPatients();

    boolean removeInpatientById(Long patientId);

    List<Inpatient> getPatientsByDoctorId(Long doctorId);

    Inpatient getInpatientById(Long patientId);

    Long addInpatient(Inpatient inpatient);

    void updateInpatient(Inpatient inpatient);
}
