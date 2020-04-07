package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;

import java.util.HashMap;
import java.util.Map;

public class DefaultAuthUserDao implements AuthUserDao {
    private Map<String, AuthUser> userByLogin;

    public DefaultAuthUserDao() {
        this.userByLogin = new HashMap<>();
        this.userByLogin.putIfAbsent("Virologist",
                new AuthUser("Virologist", "Virologist", Role.Doctor));
        this.userByLogin.putIfAbsent("Therapist",
                new AuthUser("Therapist", "Therapist", Role.Doctor));
        this.userByLogin.putIfAbsent("Surgeon",
                new AuthUser("Surgeon", "Surgeon", Role.Doctor));
        this.userByLogin.putIfAbsent("User",
                new AuthUser("User", "User", Role.AuthorizedUser));
        this.userByLogin.putIfAbsent("User01",
                new AuthUser("User01", "User01", Role.AuthorizedUser));
    }

    private static volatile AuthUserDao instance;

    public static AuthUserDao getInstance() {
        AuthUserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (AuthUserDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultAuthUserDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public AuthUser getByLogin(String login) {
        return userByLogin.get(login);
    }

    @Override
    public void saveAuthUser(AuthUser user) {
        userByLogin.putIfAbsent(user.getLogin(), user);
    }
}
