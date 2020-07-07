package com.github.KostyaTr.hospital.model;

import java.time.LocalDate;

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
    private LocalDate birthday;
    private boolean insurance;
}
