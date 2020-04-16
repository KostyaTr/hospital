package com.github.KostyaTr.hospital.dao.display;

import com.github.KostyaTr.hospital.model.display.Inpatient;

import java.util.List;

public interface InpatientDao {
    List<Inpatient> getInpatientsByDoctorId(Long doctorId);

    Inpatient getInpatientById(Long patientId);
}
