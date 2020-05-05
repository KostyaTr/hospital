package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class GuestPatient {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long doctorId;
    private int couponNum;
    private Long medicalServiceId;
    private Date visitDate;

    public GuestPatient(Long patientId, String firstName, String lastName,
                        String phoneNumber, String email, Long doctorId, int couponNum,
                        Long medicalServiceId, Date visitDate) {

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

    public int getCouponNum() {
        return couponNum;
    }

    public Long getMedicalServiceId() {
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
