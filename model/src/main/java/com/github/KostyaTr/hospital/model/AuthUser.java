package com.github.KostyaTr.hospital.model;

public class AuthUser {
    private Long authUserId;
    private String login;
    private String password;
    private Role role;
    private Long userId;

    public AuthUser(Long authUserId, String login,
                    String password, Role role, Long userId) {

        this.authUserId = authUserId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    public Long getAuthUserId() {
        return authUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
