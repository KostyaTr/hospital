package com.github.KostyaTr.hospital.web.rq;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateTreatmentCourseRq {
    private String medicineName;
    private String drugDose;
    private String timesPerDay;
    private String durationInDays;
    private String receptionDesc;
    private boolean overdose;
}
