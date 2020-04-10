package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.GuestPatient;
import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.Patient;

import java.util.List;

public interface HeadPhysicianService extends MedDoctorService {
    boolean fireDoctor(MedDoctor medDoctor);

    List<Patient> getPatientsByDeptId(Long deptId);

    List<GuestPatient> getGuestPatientByDeptId(Long deptId);
}
