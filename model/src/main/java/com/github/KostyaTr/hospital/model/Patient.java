package com.github.KostyaTr.hospital.model;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Patient extends User {
    private Long patientId;
    private MedDoctor doctor;
    private int couponNum;
    private MedicalService medicalService;
    private LocalDateTime visitDate;

    public Patient(Long userId, String firstName, String lastName, String phoneNumber,
                   String email, Long patientId, MedDoctor medDoctor, int couponNum,
                   MedicalService medicalService, LocalDateTime visitDate) {

        super(userId, firstName, lastName, phoneNumber, email);
        this.patientId = patientId;
        this.doctor = medDoctor;
        this.couponNum = couponNum;
        this.medicalService = medicalService;
        this.visitDate = visitDate;
    }
}
