package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class GuestPatient {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long couponNum;
    private Date visitDate;
    private String doctorName;
    private String medicalService;

    public GuestPatient(Long patientId, String firstName, String lastName, String phoneNumber,
                        String email, Long couponNum, Date visitDate, String doctorName,
                        String medicalService) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.couponNum = couponNum;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.medicalService = medicalService;
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
