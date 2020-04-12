package com.github.KostyaTr.hospital.dao;

import java.util.List;
import com.github.KostyaTr.hospital.model.DoctorSpecialityDept;

public interface DoctorSpecialityDeptDao {
    List<DoctorSpecialityDept> getDoctors();

    List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId);

    DoctorSpecialityDept getDoctorById(Long doctorId);
}
