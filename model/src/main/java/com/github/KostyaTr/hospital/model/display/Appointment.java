package com.github.KostyaTr.hospital.model.display;

import java.util.Date;

public class Appointment {
    private Long appointmentId;
    private Long couponNum;
    private String doctorName;
    private String serviceName;
    private Date visitDate;

    public Appointment(Long appointmentId, Long couponNum, String doctorName,
                       String serviceName, Date visitDate) {

        this.appointmentId = appointmentId;
        this.couponNum = couponNum;
        this.doctorName = doctorName;
        this.serviceName = serviceName;
        this.visitDate = visitDate;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getCouponNum() {
        return couponNum;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Date getVisitDate() {
        return visitDate;
    }
}
