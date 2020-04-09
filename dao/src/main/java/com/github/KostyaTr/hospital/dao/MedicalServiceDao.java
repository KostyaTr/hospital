package com.github.KostyaTr.hospital.dao;

import java.util.List;

public interface MedicalServiceDao {
    List<String> getMedicalServices();

    Double getPriceOfMedicalServiceById(Long medicalServiceId);

    Long getNeededSpecialityId(Long medicalServiceId);
}
