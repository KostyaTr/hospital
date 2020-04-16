package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.MedicalService;

import java.util.List;

public interface MedicalServiceDao {
    List<MedicalService> getMedicalServices();

    MedicalService getMedicalServiceById(Long medicalServiceId);
}
