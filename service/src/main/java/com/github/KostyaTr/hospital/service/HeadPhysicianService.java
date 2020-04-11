package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.*;

import java.util.List;

public interface
HeadPhysicianService extends MedDoctorService {
    boolean fireDoctor(Long doctorId);

    List<Patient> getPatientsByDeptId(Long deptId);

    List<GuestPatient> getGuestPatientByDeptId(Long deptId);

    Long addMedicalService(MedicalService medicalService);

    List<Inpatient> getInpatientsByDepId(Long deptId);

    List<Inpatient> getUndiagnosedInpatientsByDep(Long deptId);

    Long addDoctor(MedDoctor medDoctor);
}
