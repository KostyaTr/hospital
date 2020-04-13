package com.github.KostyaTr.hospital.model.display;

import java.util.Date;

public class GuestPatient {
    private Long patientId;
    private String patientName;
    private String medicalService;
    private Date visitDate;

    public GuestPatient(Long patientId, String patientName, String medicalService, Date visitDate) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.medicalService = medicalService;
        this.visitDate = visitDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getMedicalService() {
        return medicalService;
    }

    public Date getVisitDate() {
        return visitDate;
    }
}
