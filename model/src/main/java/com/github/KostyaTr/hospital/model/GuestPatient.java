package com.github.KostyaTr.hospital.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class GuestPatient {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private MedDoctor doctor;
    private int couponNum;
    private MedicalService medicalService;
    private Date visitDate;
}
