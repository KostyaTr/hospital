package com.github.KostyaTr.hospital.model;

public class MedDoctor {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String speciality;
    private String department;
    private boolean headOfDept;

    public MedDoctor(Long doctorId, String firstName, String lastName,
                     String phoneNumber, String speciality,
                     String department, boolean headOfDept) {

        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.speciality = speciality;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public boolean isHeadOfDept() {
        return headOfDept;
    }
}
