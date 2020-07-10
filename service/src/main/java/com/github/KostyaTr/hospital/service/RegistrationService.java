package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {
    Long saveUser(User user, AuthUser authUser, Card card);

    boolean loginCheck(String login);

    boolean passwordCheck(String password, String passwordRepeat);
}
