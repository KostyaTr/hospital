package com.github.KostyaTr.hospital.model;

public class MedDoctor {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String speciality;
    private Long deptNum;
    private boolean headOfDept;

    public MedDoctor(Long doctorId, String firstName, String lastName,
                     String phoneNumber, String speciality,
                     Long deptNum, boolean headOfDept) {

        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.speciality = speciality;
        this.deptNum = deptNum;
        this.headOfDept = headOfDept;
    }

    public Long getDoctorId() {
        return doctorId;
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

    public String getSpeciality() {
        return speciality;
    }

    public Long getDeptNum() {
        return deptNum;
    }

    public boolean isHeadOfDept() {
        return headOfDept;
    }
}
