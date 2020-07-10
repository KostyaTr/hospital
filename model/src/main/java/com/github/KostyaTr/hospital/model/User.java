package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
