package com.github.KostyaTr.hospital.dao;


import java.util.List;

public interface DoctorSpecialityDao {
    List<Long> getDoctorsSpecialities(Long doctorId);
}
