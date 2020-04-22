package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class Inpatient {
    private Long inpatientId;
    private Long userId;
    private Long doctorId;
    private Long deptChamberId;
    private String diagnose;
    private Long treatmentCourseId;
    private Long operationServiceId;
    private Status status;
    private Date enrollmentDate;

    public Inpatient(Long inpatientId, Long userId, Long doctorId, Long deptChamberId,
                     String diagnose, Long treatmentCourseId, Long operationServiceId,
                     Status status, Date enrollmentDate) {

        this.inpatientId = inpatientId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.deptChamberId = deptChamberId;
        this.diagnose = diagnose;
        this.treatmentCourseId = treatmentCourseId;
        this.operationServiceId = operationServiceId;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getInpatientId() {
        return inpatientId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getDeptChamberId() {
        return deptChamberId;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public Long getTreatmentCourseId() {
        return treatmentCourseId;
    }

    public Long getOperationServiceId() {
        return operationServiceId;
    }

    public Status getStatus() {
        return status;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }
}
