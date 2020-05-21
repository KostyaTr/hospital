package com.github.KostyaTr.hospital.model;

import java.util.Date;

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


    public GuestPatient(Long patientId, String firstName, String lastName,
                        String phoneNumber, String email, MedDoctor doctor,
                        int couponNum, MedicalService medicalService, Date visitDate) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.doctor = doctor;
        this.couponNum = couponNum;
        this.medicalService = medicalService;
        this.visitDate = visitDate;
    }

    public MedDoctor getDoctor() {
        return doctor;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Date getVisitDate() {
        return visitDate;
    }
}
