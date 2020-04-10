package com.github.KostyaTr.hospital.model;


import java.util.Date;

public class Patient {
    private Long patientId;
    private Long userId;
    private Long doctorId;
    private Long couponNum;
    private String medicalServiceId;
    private Date visitDate;

    public Patient(Long patientId, Long userId, Long doctorId,
                   Long couponNum, String medicalServiceId, Date visitDate) {

        this.patientId = patientId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.couponNum = couponNum;
        this.medicalServiceId = medicalServiceId;
        this.visitDate = visitDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getUserId() {
        return userId;
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

    public Date getVisitDate() {
        return visitDate;
    }
}
