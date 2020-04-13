package com.github.KostyaTr.hospital.dao.display;

import java.util.List;
import com.github.KostyaTr.hospital.model.display.DoctorSpecialityDept;

public interface DoctorSpecialityDeptDao {
    List<DoctorSpecialityDept> getDoctors();

    List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId);

    DoctorSpecialityDept getDoctorById(Long doctorId);

    DoctorSpecialityDept getDoctorByUserId(Long userId);
}
