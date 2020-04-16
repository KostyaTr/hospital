package com.github.KostyaTr.hospital.dao;

public interface SpecialityDao {
    String getSpecialityById(Long specialityId);

    Long addSpeciality(String specialityName);
}
