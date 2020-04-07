package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.MedDoctor;

import java.util.List;

public interface MedDoctorDao {
    List<MedDoctor> getDoctors();

    MedDoctor getDoctorByLogin(String login);

    boolean removeDoctor(MedDoctor medDoctor);
}
