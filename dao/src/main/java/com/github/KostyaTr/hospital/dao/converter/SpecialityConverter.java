package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.SpecialityEntity;
import com.github.KostyaTr.hospital.model.Speciality;

public class SpecialityConverter {
    public static Speciality fromEntity(SpecialityEntity specialityEntity) {
        if (specialityEntity == null) {
            return null;
        }
        return new Speciality(
                specialityEntity.getSpecialityId(),
                specialityEntity.getSpeciality());
    }

    public static SpecialityEntity toEntity(Speciality speciality) {
        if (speciality == null) {
            return null;
        }
        final SpecialityEntity specialityEntity = new SpecialityEntity();
        specialityEntity.setSpecialityId(speciality.getSpecialityId());
        specialityEntity.setSpeciality(speciality.getSpecialityName());
        return specialityEntity;
    }
}
