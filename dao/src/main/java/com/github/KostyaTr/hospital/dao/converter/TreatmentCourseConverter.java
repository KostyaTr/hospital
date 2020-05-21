package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.TreatmentCourseEntity;
import com.github.KostyaTr.hospital.model.Medicine;
import com.github.KostyaTr.hospital.model.TreatmentCourse;

public class TreatmentCourseConverter {
    public static TreatmentCourse fromEntity(TreatmentCourseEntity treatmentCourseEntity) {
        if (treatmentCourseEntity == null) {
            return null;
        }
        return new TreatmentCourse(
                treatmentCourseEntity.getMedicine().getMedicineId(),
                treatmentCourseEntity.getMedicine().getMedicineName(),
                treatmentCourseEntity.getMedicine().getNormalDose(),
                treatmentCourseEntity.getMedicine().getCriticalDose(),
                treatmentCourseEntity.getMedicine().getPackageAmount(),
                treatmentCourseEntity.getMedicine().getPrice(),
                treatmentCourseEntity.getTreatmentCourseId(),
                treatmentCourseEntity.getMedicineDose(),
                treatmentCourseEntity.getReceptionDesc(),
                treatmentCourseEntity.getTimesPerDay(),
                treatmentCourseEntity.getDurationInDays());
    }

    public static TreatmentCourseEntity toEntity(TreatmentCourse treatmentCourse) {
        if (treatmentCourse == null) {
            return null;
        }
        final TreatmentCourseEntity treatmentCourseEntity = new TreatmentCourseEntity();
        treatmentCourseEntity.setMedicine(
                MedicineConverter.toEntity(
                        new Medicine(
                                treatmentCourse.getMedicineId(),
                                treatmentCourse.getMedicineName(),
                                treatmentCourse.getNormalDose(),
                                treatmentCourse.getCriticalDose(),
                                treatmentCourse.getPackageAmount(),
                                treatmentCourse.getPrice()
                        )
                )
        );
        treatmentCourseEntity.setTreatmentCourseId(treatmentCourse.getTreatmentCourseId());
        treatmentCourseEntity.setMedicineDose(treatmentCourse.getMedicineDose());
        treatmentCourseEntity.setReceptionDesc(treatmentCourse.getReceptionDesc());
        treatmentCourseEntity.setTimesPerDay(treatmentCourse.getTimesPerDay());
        treatmentCourseEntity.setDurationInDays(treatmentCourse.getDurationInDays());
        return treatmentCourseEntity;
    }
}
