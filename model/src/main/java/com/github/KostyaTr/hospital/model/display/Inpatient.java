package com.github.KostyaTr.hospital.model.display;

import java.util.Date;

public class Inpatient {
    private Long inpatientId;
    private String patientName;
    private Long chamberId;
    private String diagnose;
    private String medicineName;
    private Double medicineDose;
    private String status;
    private Date enrollmentDate;

    public Inpatient(Long inpatientId, String patientName, Long chamberId, String diagnose, String medicineName,
                     Double medicineDose, String status, Date enrollmentDate) {

        this.inpatientId = inpatientId;
        this.patientName = patientName;
        this.chamberId = chamberId;
        this.diagnose = diagnose;
        this.medicineName = medicineName;
        this.medicineDose = medicineDose;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getInpatientId() {
        return inpatientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getChamberId() {
        return chamberId;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Double getMedicineDose() {
        return medicineDose;
    }

    public String getStatus() {
        return status;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }
}
