package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class Inpatient extends User {
    private Long inpatientId;
    private MedDoctor doctor;
    private Chamber chamber;
    private String diagnose;
    private TreatmentCourse treatmentCourse;
    private Status status;
    private Date enrollmentDate;

    public Inpatient(Long userId, String firstName, String lastName,
                     String phoneNumber, String email, Long inpatientId,
                     MedDoctor doctor, Chamber chamber, String diagnose,
                     TreatmentCourse treatmentCourse, Status status, Date enrollmentDate) {

        super(userId, firstName, lastName, phoneNumber, email);
        this.inpatientId = inpatientId;
        this.doctor = doctor;
        this.chamber = chamber;
        this.diagnose = diagnose;
        this.treatmentCourse = treatmentCourse;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getInpatientId() {
        return inpatientId;
    }

    public MedDoctor getDoctor() {
        return doctor;
    }

    public Chamber getChamber() {
        return chamber;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public TreatmentCourse getTreatmentCourse() {
        return treatmentCourse;
    }

    public Status getStatus() {
        return status;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }
}
