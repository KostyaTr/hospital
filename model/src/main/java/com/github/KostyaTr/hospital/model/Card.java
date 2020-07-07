package com.github.KostyaTr.hospital.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Card {
    private Long cardId;
    private Long userId;
    private String history;
    private String address;
    private Date birthday;
    private boolean insurance;
}
