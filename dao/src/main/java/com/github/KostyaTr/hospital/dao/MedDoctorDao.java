package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.MedDoctor;

import java.util.List;

public interface MedDoctorDao {
    List<MedDoctor> getDoctors();

    MedDoctor getDoctorByUserId(Long userId);

    boolean removeDoctorById(Long doctorId);

    MedDoctor getHeadPhysicianByDepartment(Long deptId);

    List<MedDoctor>  getHeadPhysicians();
}
