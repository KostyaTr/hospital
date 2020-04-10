package com.github.KostyaTr.hospital.dao;


import com.github.KostyaTr.hospital.model.DischargedPatient;

import java.util.List;

public interface DischargedPatientDao {
    Long addDischargedPatient(DischargedPatient dischargedPatient);

    List<DischargedPatient> getDischargedPatients();

    DischargedPatient getDischargedPatientById(DischargedPatient dischargedPatientId);
}
