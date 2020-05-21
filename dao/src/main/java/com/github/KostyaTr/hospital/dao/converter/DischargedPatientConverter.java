package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.DischargedPatientEntity;
import com.github.KostyaTr.hospital.model.DischargedPatient;

public class DischargedPatientConverter {
    public static DischargedPatient fromEntity(DischargedPatientEntity dischargedPatientEntity) {
        if (dischargedPatientEntity == null) {
            return null;
        }
        return new DischargedPatient(
                dischargedPatientEntity.getDischargedPatientId(),
                dischargedPatientEntity.getPatientName(),
                dischargedPatientEntity.getDoctorName(),
                dischargedPatientEntity.getDiagnose(),
                dischargedPatientEntity.getCardHistory(),
                dischargedPatientEntity.getTreatmentCourse(),
                dischargedPatientEntity.getStatus(),
                dischargedPatientEntity.getEnrollmentDate(),
                dischargedPatientEntity.getDischargeDate()
        );
    }

    public static DischargedPatientEntity toEntity(DischargedPatient dischargedPatient) {
        if (dischargedPatient == null) {
            return null;
        }
        final DischargedPatientEntity dischargedPatientEntity = new DischargedPatientEntity();
        dischargedPatientEntity.setDischargedPatientId(dischargedPatient.getDischargedPatientId());
        dischargedPatientEntity.setPatientName(dischargedPatient.getPatientName());
        dischargedPatientEntity.setDoctorName(dischargedPatient.getDoctorName());
        dischargedPatientEntity.setDiagnose(dischargedPatient.getDiagnose());
        dischargedPatientEntity.setCardHistory(dischargedPatient.getCardHistory());
        dischargedPatientEntity.setTreatmentCourse(dischargedPatient.getTreatmentCourse());
        dischargedPatientEntity.setStatus(dischargedPatient.getStatus());
        dischargedPatientEntity.setEnrollmentDate(dischargedPatient.getEnrollmentDate());
        dischargedPatientEntity.setDischargeDate(dischargedPatient.getDischargeDate());
        return dischargedPatientEntity;
    }
}
