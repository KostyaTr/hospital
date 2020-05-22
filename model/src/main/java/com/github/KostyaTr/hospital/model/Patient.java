package com.github.KostyaTr.hospital.model;


import java.util.Date;

public class Patient extends User {
    private Long patientId;
    private MedDoctor medDoctor;
    private int couponNum;
    private MedicalService medicalService;
    private Date visitDate;

    public Patient(Long userId, String firstName, String lastName, String phoneNumber,
                   String email, Long patientId, MedDoctor medDoctor, int couponNum,
                   MedicalService medicalService, Date visitDate) {

        super(userId, firstName, lastName, phoneNumber, email);
        this.patientId = patientId;
        this.medDoctor = medDoctor;
        this.couponNum = couponNum;
        this.medicalService = medicalService;
        this.visitDate = visitDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public MedDoctor getDoctor() {
        return medDoctor;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
