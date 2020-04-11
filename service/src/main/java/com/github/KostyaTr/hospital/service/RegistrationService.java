package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.User;

public interface RegistrationService {
    Long saveUser(User user);

    Long saveAuthUser(AuthUser user);

    Long saveCard(Card card);

    boolean loginCheck(String login);

    boolean passwordCheck(String password, String passwordRepeat);
}
