package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class DischargedPatient {
    private Long dischargedPatientId;
    private Long inpatientId;
    private String patientStatus;
    private Date dischargeDate;

    public DischargedPatient(Long dischargedPatientId, Long inpatientId,
                             String patientStatus, Date dischargeDate) {

        this.dischargedPatientId = dischargedPatientId;
        this.inpatientId = inpatientId;
        this.patientStatus = patientStatus;
        this.dischargeDate = dischargeDate;
    }

    public Long getDischargedPatientId() {
        return dischargedPatientId;
    }

    public Long getInpatientId() {
        return inpatientId;
    }

    public String getPatientStatus() {
        return patientStatus;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }
}
