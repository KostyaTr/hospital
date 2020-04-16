package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Inpatient;

import java.util.List;

public interface InpatientDao {
    List<Inpatient> getInpatientsByDoctorId(Long doctorId);

    List<Inpatient> getInpatientsByDepId(Long deptId);

    List<Inpatient> getUndiagnosedInpatientsByDep(Long deptId);

    List<Inpatient> getUndiagnosedInpatientsByDoctor(Long doctorId);

    List<Inpatient> getPatients();

    boolean removeInpatientById(Long patientId);

    Inpatient getInpatientById(Long patientId);

    Long addInpatient(Inpatient inpatient);

    boolean updateDiagnose(Long patientId, String diagnose);

    boolean updateStatus(Long patientId, String status);

    boolean updateTreatmentCourse(Long patientId, Long treatmentCourseId);
}
