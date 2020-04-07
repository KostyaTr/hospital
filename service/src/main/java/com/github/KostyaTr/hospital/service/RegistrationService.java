package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.User;

public interface RegistrationService {
    void saveUser(User user);

    void saveAuthUser(AuthUser user);

    boolean loginCheck(String login);

    boolean passwordCheck(String password, String passwordRepeat);
}
