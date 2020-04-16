package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultCardDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Card;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.service.RegistrationService;

public class DefaultRegistrationService implements RegistrationService {
    private UserDao userDao = DefaultUserDao.getInstance();
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();
    private CardDao cardDao = DefaultCardDao.getInstance();

    private static class SingletonHolder {
        static final RegistrationService HOLDER_INSTANCE = new DefaultRegistrationService();
    }

    public static RegistrationService getInstance() {
        return DefaultRegistrationService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Long saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public Long saveAuthUser(AuthUser authUser) {
        return authUserDao.saveAuthUser(authUser);
    }

    @Override
    public Long saveCard(Card card) {
        return cardDao.addCard(card);
    }

    @Override
    public boolean loginCheck(String login) {
        return authUserDao.getByLogin(login) == null; //true if login is not registered
    }

    @Override
    public boolean passwordCheck(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }
}
