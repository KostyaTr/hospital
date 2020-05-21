package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.InpatientEntity;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.User;

public class InpatientConverter {
    public static Inpatient fromEntity(InpatientEntity inpatientEntity) {
        if (inpatientEntity == null) {
            return null;
        }
        return new Inpatient(
                inpatientEntity.getUser().getUserId(),
                inpatientEntity.getUser().getFirstName(),
                inpatientEntity.getUser().getLastName(),
                inpatientEntity.getUser().getPhoneNumber(),
                inpatientEntity.getUser().getEmail(),
                inpatientEntity.getInpatientId(),
                MedDoctorConverter.fromEntity(inpatientEntity.getDoctor()),
                ChamberConverter.fromEntity(inpatientEntity.getChamber()),
                inpatientEntity.getDiagnose(),
                TreatmentCourseConverter.fromEntity(inpatientEntity.getTreatmentCourse()),
                inpatientEntity.getStatus(),
                inpatientEntity.getEnrollmentDate());
    }

    public static InpatientEntity toEntity(Inpatient inpatient) {
        if (inpatient == null) {
            return null;
        }
        final InpatientEntity inpatientEntity = new InpatientEntity();
        inpatientEntity.setChamber(ChamberConverter.toEntity(inpatient.getChamber()));
        inpatientEntity.setDiagnose(inpatient.getDiagnose());
        inpatientEntity.setDoctor(MedDoctorConverter.toEntity(inpatient.getDoctor()));
        inpatientEntity.setEnrollmentDate(inpatient.getEnrollmentDate());
        inpatientEntity.setStatus(inpatient.getStatus());
        inpatientEntity.setTreatmentCourse(TreatmentCourseConverter.toEntity(inpatient.getTreatmentCourse()));
        inpatientEntity.setInpatientId(inpatient.getInpatientId());
        inpatientEntity.setUser(
                UserConverter.toEntity(
                        new User(
                                inpatient.getUserId(),
                                inpatient.getFirstName(),
                                inpatient.getLastName(),
                                inpatient.getPhoneNumber(),
                                inpatient.getEmail()
                        )
                )
        );
        return inpatientEntity;
    }
}
