package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class DischargedPatient {
    private Long dischargedPatientId;
    private String patientName;
    private String doctorName;
    private String diagnose;
    private String cardHistory;
    private String treatmentCourse;
    private Status status;
    private Date enrollmentDate;
    private Date dischargeDate;

    public DischargedPatient(Long dischargedPatientId, String patientName, String doctorName,
                             String diagnose, String cardHistory, String treatmentCourse,
                             Status status, Date enrollmentDate, Date dischargeDate) {

        this.dischargedPatientId = dischargedPatientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diagnose = diagnose;
        this.cardHistory = cardHistory;
        this.treatmentCourse = treatmentCourse;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
        this.dischargeDate = dischargeDate;
    }

    public Long getDischargedPatientId() {
        return dischargedPatientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getCardHistory() {
        return cardHistory;
    }

    public String getTreatmentCourse() {
        return treatmentCourse;
    }

    public Status getStatus() {
        return status;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }
}
