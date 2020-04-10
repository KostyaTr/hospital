package com.github.KostyaTr.hospital.model;

import java.util.Date;

public class Card {
    private Long cardId;
    private Long userId;
    private String history;
    private String address;
    private Date birthday;
    private boolean insurance;

    public Card(Long cardId, Long userId, String history,
                String address, Date birthday, boolean insurance) {

        this.cardId = cardId;
        this.userId = userId;
        this.history = history;
        this.address = address;
        this.birthday = birthday;
        this.insurance = insurance;
    }

    public Long getCardId() {
        return cardId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getHistory() {
        return history;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public boolean isInsurance() {
        return insurance;
    }
}
