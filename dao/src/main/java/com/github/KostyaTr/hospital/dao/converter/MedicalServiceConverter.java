package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.MedicalServiceEntity;
import com.github.KostyaTr.hospital.model.MedicalService;

public class MedicalServiceConverter {
    public static MedicalService fromEntity(MedicalServiceEntity medicalServiceEntity) {
        if (medicalServiceEntity == null) {
            return null;
        }
        return new MedicalService(
                medicalServiceEntity.getMedicalServiceId(),
                medicalServiceEntity.getServiceName(),
                SpecialityConverter.fromEntity(medicalServiceEntity.getSpeciality()),
                medicalServiceEntity.getServiceCost());
    }

    public static MedicalServiceEntity toEntity(MedicalService medicalService) {
        if (medicalService == null) {
            return null;
        }
        final MedicalServiceEntity medicalServiceEntity = new MedicalServiceEntity();
        medicalServiceEntity.setMedicalServiceId(medicalService.getMedicalServiceId());
        medicalServiceEntity.setServiceName(medicalService.getServiceName());
        medicalServiceEntity.setSpeciality(SpecialityConverter.toEntity(medicalService.getNeededSpeciality()));
        medicalServiceEntity.setServiceCost(medicalService.getServiceCost());
        return medicalServiceEntity;
    }
}
