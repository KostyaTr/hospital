package com.github.KostyaTr.hospital.model;


import java.util.Date;

public class Patient {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long cardNum;
    private String doctorName;
    private Long couponNum;
    private String medicalService;
    private Date visitDate;

    public Patient(Long patientId, String firstName, String lastName,
                   String phoneNumber, String email, Long cardNum, String doctorName,
                   Long couponNum, String medicalService, Date visitDate) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cardNum = cardNum;
        this.doctorName = doctorName;
        this.couponNum = couponNum;
        this.medicalService = medicalService;
        this.visitDate = visitDate;
    }

    public Long getCardNum() {
        return cardNum;
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

    public String getDoctorName() {
        return doctorName;
    }

    public Long getCouponNum() {
        return couponNum;
    }

    public String getMedicalService() {
        return medicalService;
    }

    public Date getVisitDate() {
        return visitDate;
    }
}
