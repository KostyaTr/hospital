package com.github.KostyaTr.hospital.model.display;

public class TreatmentCourse {
    private Long treatmentCourseId;
    private String medicineName;
    private Double medicineDose;
    private String receptionDesc;
    private Integer timesPerDay;
    private Integer durationInDays;

    public TreatmentCourse(Long treatmentCourseId, String medicineName, Double medicineDose,
                           String receptionDesc, Integer timesPerDay,
                           Integer durationInDays) {

        this.treatmentCourseId = treatmentCourseId;
        this.medicineName = medicineName;
        this.medicineDose = medicineDose;
        this.receptionDesc = receptionDesc;
        this.timesPerDay = timesPerDay;
        this.durationInDays = durationInDays;
    }

    public Long getTreatmentCourseId() {
        return treatmentCourseId;
    }

    public String getMedicineName() {
        return medicineName;
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
