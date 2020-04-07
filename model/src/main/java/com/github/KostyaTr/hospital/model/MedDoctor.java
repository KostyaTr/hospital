package com.github.KostyaTr.hospital.model;

public class MedDoctor {
    private String login;
    private String firstName;
    private String lastName;
    private String speciality;
    private String phoneNumber;
    private String email;

    public MedDoctor(String login, String firstName, String lastName,
                     String speciality, String phoneNumber, String email) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
