package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.MedDoctorEntity;
import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.User;

import java.util.stream.Collectors;

public class MedDoctorConverter {
    public static MedDoctor fromEntity(MedDoctorEntity medDoctorEntity) {
        if (medDoctorEntity == null) {
            return null;
        }
        return new MedDoctor(
                medDoctorEntity.getUser().getUserId(),
                medDoctorEntity.getUser().getFirstName(),
                medDoctorEntity.getUser().getLastName(),
                medDoctorEntity.getUser().getPhoneNumber(),
                medDoctorEntity.getUser().getEmail(),
                medDoctorEntity.getDoctorId(),
                DepartmentConverter.fromEntity(medDoctorEntity.getDepartment()),
                medDoctorEntity.getSpecialities().stream()
                        .map(SpecialityConverter::fromEntity)
                        .collect(Collectors.toList()),
                medDoctorEntity.isHeadOfDept()
        );
    }

    public static MedDoctorEntity toEntity(MedDoctor medDoctor) {
        if (medDoctor == null) {
            return null;
        }
        final MedDoctorEntity medDoctorEntity = new MedDoctorEntity();
        medDoctorEntity.setSpecialities(
                medDoctor.getSpecialityList().stream()
                        .map(SpecialityConverter::toEntity)
                        .collect(Collectors.toList())
        );
        medDoctorEntity.setUser(
                UserConverter.toEntity(
                        new User(
                                medDoctor.getUserId(),
                                medDoctor.getFirstName(),
                                medDoctor.getLastName(),
                                medDoctor.getPhoneNumber(),
                                medDoctor.getEmail()
                        )
                )
        );
        medDoctorEntity.setDoctorId(medDoctor.getDoctorId());
        medDoctorEntity.setHeadOfDept(medDoctor.isHeadOfDept());
        return medDoctorEntity;
    }
}
