package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class GuestPatient {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long doctorId;
    private Long couponNum;
    private String medicalServiceId;
    private Date visitDate;

    public GuestPatient(Long patientId, String firstName, String lastName,
                        String phoneNumber, String email, Long doctorId, Long couponNum,
                        String medicalServiceId, Date visitDate) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.doctorId = doctorId;
        this.couponNum = couponNum;
        this.medicalServiceId = medicalServiceId;
        this.visitDate = visitDate;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getCouponNum() {
        return couponNum;
    }

    public String getMedicalServiceId() {
        return medicalServiceId;
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
