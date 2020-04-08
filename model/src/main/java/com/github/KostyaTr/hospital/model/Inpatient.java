package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class Inpatient {
    private Long inpatientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long cardNum;
    private String doctorName;
    private String department;
    private Long chamberNum;
    private String diagnose;
    private String treatmentCourse;
    private String operationService;
    private Date enrollmentDate;

    public Inpatient(Long inpatientId, String firstName, String lastName, String phoneNumber,
                     String email, Long cardNum, String doctorName, String department, Long chamberNum,
                     String diagnose, String treatmentCourse, String operationService, Date enrollmentDate) {

        this.inpatientId = inpatientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cardNum = cardNum;
        this.doctorName = doctorName;
        this.department = department;
        this.chamberNum = chamberNum;
        this.diagnose = diagnose;
        this.treatmentCourse = treatmentCourse;
        this.operationService = operationService;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getInpatientId() {
        return inpatientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Long getCardNum() {
        return cardNum;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public Long getChamberNum() {
        return chamberNum;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getTreatmentCourse() {
        return treatmentCourse;
    }

    public String getOperationService() {
        return operationService;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }
}
