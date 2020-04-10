package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class Card {
    private Long cardId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String history;
    private Date birthday;
    private boolean insurance;

    public Card(Long cardId, String firstName, String lastName, String phoneNumber,
                String email, String history, Date birthday, boolean insurance) {

        this.cardId = cardId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.history = history;
        this.birthday = birthday;
        this.insurance = insurance;
    }

    public Long getCardId() {
        return cardId;
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

    public String getHistory() {
        return history;
    }

    public Date getBirthday() {
        return birthday;
    }

    public boolean isInsurance() {
        return insurance;
    }
}
