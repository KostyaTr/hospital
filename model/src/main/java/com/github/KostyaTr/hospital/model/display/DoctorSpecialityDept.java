package com.github.KostyaTr.hospital.model.display;

public class DoctorSpecialityDept {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String specialities;
    private String department;

    public DoctorSpecialityDept(Long doctorId, String firstName, String lastName,
                            String phoneNumber, String email, String specialities,
                            String department) {

        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specialities = specialities;
        this.department = department;
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

    public String getEmail() {
        return email;
    }

    public String getSpecialities() {
        return specialities;
    }

    public String getDepartment() {
        return department;
    }
}
