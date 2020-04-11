package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Inpatient;
import java.util.List;

public interface InpatientDao {
    List<Inpatient> getInpatientsByDoctorId(Long doctorId);

    List<Inpatient> getInpatientsByDepChamberId(Long DepChamberId);

    List<Inpatient> getUndiagnosedInpatients();

    List<Inpatient> getPatients();

    boolean removeInpatientById(Long patientId);

    Inpatient getInpatientById(Long patientId);

    Long addInpatient(Inpatient inpatient);

    boolean updateDiagnose(Long patientId, String diagnose);

    boolean updateTreatmentCourse(Long patientId, Long treatmentCourseId);
}
