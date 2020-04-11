package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class DischargedPatient {
    private Long dischargedPatientId;
    private Long userId;
    private Long doctorId;
    private Long deptChamberId;
    private String diagnose;
    private Long treatmentCourseId;
    private Long operationServiceId;
    private String status;
    private Date enrollmentDate;
    private Date dischargeDate;

    public DischargedPatient(Long dischargedPatientId, Long userId, Long doctorId, Long deptChamberId,
                             String diagnose, Long treatmentCourseId, Long operationServiceId,
                             String status, Date enrollmentDate, Date dischargeDate) {

        this.dischargedPatientId = dischargedPatientId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.deptChamberId = deptChamberId;
        this.diagnose = diagnose;
        this.treatmentCourseId = treatmentCourseId;
        this.operationServiceId = operationServiceId;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
        this.dischargeDate = dischargeDate;
    }

    public Long getDischargedPatientId() {
        return dischargedPatientId;
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

    public String getStatus() {
        return status;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }
}
