package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.GuestPatientEntity;
import com.github.KostyaTr.hospital.model.GuestPatient;

public class GuestPatientConverter {
    public static GuestPatient fromEntity(GuestPatientEntity guestPatientEntity) {
        if (guestPatientEntity == null) {
            return null;
        }
        return new GuestPatient(
                guestPatientEntity.getPatientId(),
                guestPatientEntity.getFirstName(),
                guestPatientEntity.getLastName(),
                guestPatientEntity.getPhoneNumber(),
                guestPatientEntity.getEmail(),
                MedDoctorConverter.fromEntity(guestPatientEntity.getDoctor()),
                guestPatientEntity.getCouponNum(),
                MedicalServiceConverter.fromEntity(guestPatientEntity.getMedicalService()),
                guestPatientEntity.getVisitDate()
        );
    }

    public static GuestPatientEntity toEntity(GuestPatient guestPatient) {
        if (guestPatient == null) {
            return null;
        }
        final GuestPatientEntity guestPatientEntity = new GuestPatientEntity();
        guestPatientEntity.setPatientId(guestPatient.getPatientId());
        guestPatientEntity.setFirstName(guestPatientEntity.getFirstName());
        guestPatientEntity.setLastName(guestPatient.getLastName());
        guestPatientEntity.setPhoneNumber(guestPatient.getPhoneNumber());
        guestPatientEntity.setEmail(guestPatient.getEmail());
        guestPatientEntity.setDoctor(MedDoctorConverter.toEntity(guestPatient.getDoctor()));
        guestPatientEntity.setCouponNum(guestPatient.getCouponNum());
        guestPatientEntity.setMedicalService(MedicalServiceConverter.toEntity(guestPatient.getMedicalService()));
        guestPatientEntity.setVisitDate(guestPatient.getVisitDate());
        return guestPatientEntity;
    }
}
