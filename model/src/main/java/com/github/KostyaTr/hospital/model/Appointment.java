package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class Appointment {
    private Long userId;
    private Long doctorId;
    private Long coupon_num;
    private Long medicalServiceId;
    private Date visitDate;

    public Appointment(Long userId, Long doctorId, Long coupon_num, Long medicalServiceId, Date visitDate) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.coupon_num = coupon_num;
        this.medicalServiceId = medicalServiceId;
        this.visitDate = visitDate;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getCoupon_num() {
        return coupon_num;
    }

    public Long getMedicalServiceId() {
        return medicalServiceId;
    }

    public Date getVisitDate() {
        return visitDate;
    }
}
