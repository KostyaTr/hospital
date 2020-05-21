package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.PatientEntity;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.model.User;

public class PatientConverter {
    public static Patient fromEntity(PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }
        return new Patient(
                patientEntity.getUser().getUserId(),
                patientEntity.getUser().getFirstName(),
                patientEntity.getUser().getLastName(),
                patientEntity.getUser().getPhoneNumber(),
                patientEntity.getUser().getEmail(),
                patientEntity.getPatientId(),
                MedDoctorConverter.fromEntity(patientEntity.getDoctor()),
                patientEntity.getCouponNum(),
                MedicalServiceConverter.fromEntity(patientEntity.getMedicalService()),
                patientEntity.getVisitDate());
    }

    public static PatientEntity toEntity(Patient patient) {
        if (patient == null) {
            return null;
        }
        final PatientEntity patientEntity = new PatientEntity();
        patientEntity.setUser(
                UserConverter.toEntity(
                        new User(
                                patient.getUserId(),
                                patient.getFirstName(),
                                patient.getLastName(),
                                patient.getPhoneNumber(),
                                patient.getEmail()
                        )
                )
        );
        patientEntity.setPatientId(patient.getPatientId());
        patientEntity.setDoctor(MedDoctorConverter.toEntity(patient.getDoctor()));
        patientEntity.setCouponNum(patient.getCouponNum());
        patientEntity.setMedicalService(MedicalServiceConverter.toEntity(patient.getMedicalService()));
        patientEntity.setVisitDate(patient.getVisitDate());
        return patientEntity;
    }
}
