package com.github.KostyaTr.hospital.model;

public class TreatmentCourse {
    private Long treatmentCourseId;
    private Long medicineId;
    private Double medicineDose;
    private String receptionDesc;
    private Integer timesPerDay;
    private Integer durationInDays;

    public TreatmentCourse(Long treatmentCourseId, Long medicineId, Double medicineDose,
                           String receptionDesc, Integer timesPerDay,
                           Integer durationInDays) {

        this.treatmentCourseId = treatmentCourseId;
        this.medicineId = medicineId;
        this.medicineDose = medicineDose;
        this.receptionDesc = receptionDesc;
        this.timesPerDay = timesPerDay;
        this.durationInDays = durationInDays;
    }

    public Long getTreatmentCourseId() {
        return treatmentCourseId;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public Double getMedicineDose() {
        return medicineDose;
    }

    public String getReceptionDesc() {
        return receptionDesc;
    }

    public Integer getTimesPerDay() {
        return timesPerDay;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }
}
