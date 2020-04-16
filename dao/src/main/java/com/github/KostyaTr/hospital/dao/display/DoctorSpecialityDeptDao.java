package com.github.KostyaTr.hospital.dao.display;

import com.github.KostyaTr.hospital.model.display.DoctorSpecialityDept;

import java.util.List;

public interface DoctorSpecialityDeptDao {
    List<DoctorSpecialityDept> getDoctors();

    List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId);

    DoctorSpecialityDept getDoctorById(Long doctorId);
}
