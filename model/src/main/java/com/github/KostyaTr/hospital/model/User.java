package com.github.KostyaTr.hospital.model;

public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public User(Long userId, String firstName, String lastName,
                String phoneNumber, String email) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
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
}
