package com.github.KostyaTr.hospital.web.rq;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpUserRq {
    private String login;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String sex;
    private String address;
    private String birthday;
    private boolean insurance;
    private String password;
    private String passwordRepeat;
}
