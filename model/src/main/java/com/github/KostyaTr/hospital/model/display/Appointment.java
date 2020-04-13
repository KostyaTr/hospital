package com.github.KostyaTr.hospital.model.display;

import java.util.Date;

public class Appointment {
    private Long couponNum;
    private String doctorName;
    private String serviceName;
    private Date visitDate;

    public Appointment(Long couponNum, String doctorName, String serviceName, Date visitDate) {
        this.couponNum = couponNum;
        this.doctorName = doctorName;
        this.serviceName = serviceName;
        this.visitDate = visitDate;
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
