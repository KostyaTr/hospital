package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.RegistrationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class DefaultRegistrationService implements RegistrationService {
    private final UserDao userDao;
    private final AuthUserDao authUserDao;
    private final CardDao cardDao;
    private final PasswordEncoder passwordEncoder;

    public DefaultRegistrationService(UserDao userDao, AuthUserDao authUserDao, CardDao cardDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.authUserDao = authUserDao;
        this.cardDao = cardDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public Long saveUser(User user, AuthUser authUser, Card card) {
        Long userId = userDao.saveUser(user);
        authUser.setUserId(userId);
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUserDao.saveAuthUser(authUser);
        card.setUserId(userId);
        cardDao.addCard(card);
        return userId;
    }

    @Override
    @Transactional
    public boolean loginCheck(String login) {
        return authUserDao.getByLogin(login) == null; //true if login is not registered
    }

    @Override
    public boolean passwordCheck(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }
}
