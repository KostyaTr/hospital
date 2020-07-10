package com.github.KostyaTr.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TreatmentCourse extends Medicine {
    private Long treatmentCourseId;
    private Double medicineDose;
    private String receptionDesc;
    private Integer timesPerDay;
    private Integer durationInDays;

    public TreatmentCourse(Long medicineId, String medicineName, Double normalDose,
                           Double criticalDose, Integer packageAmount, Double price,
                           Long treatmentCourseId, Double medicineDose, String receptionDesc,
                           Integer timesPerDay, Integer durationInDays) {

        super(medicineId, medicineName, normalDose, criticalDose, packageAmount, price);
        this.treatmentCourseId = treatmentCourseId;
        this.medicineDose = medicineDose;
        this.receptionDesc = receptionDesc;
        this.timesPerDay = timesPerDay;
        this.durationInDays = durationInDays;
    }
}
