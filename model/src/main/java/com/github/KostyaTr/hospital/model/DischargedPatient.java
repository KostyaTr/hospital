package com.github.KostyaTr.hospital.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DischargedPatient {
    private Long dischargedPatientId;
    private String patientName;
    private String doctorName;
    private String diagnose;
    private String cardHistory;
    private String treatmentCourse;
    private Status status;
    private LocalDate enrollmentDate;
    private LocalDate dischargeDate;
}
