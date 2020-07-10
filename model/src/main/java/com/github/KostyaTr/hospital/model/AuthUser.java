package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthUser {
    private Long authUserId;
    private String login;
    private String password;
    private Role role;
    private Long userId;
}
