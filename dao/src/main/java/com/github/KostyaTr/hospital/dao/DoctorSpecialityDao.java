package com.github.KostyaTr.hospital.dao;


import java.util.List;

public interface DoctorSpecialityDao {
    List<Integer> getDoctorsSpecialities(Long doctorId);
}
