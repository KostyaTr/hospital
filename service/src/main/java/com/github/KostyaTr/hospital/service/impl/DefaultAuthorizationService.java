package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.service.AuthorizationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultAuthorizationService implements AuthorizationService {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;

    public DefaultAuthorizationService(AuthUserDao authUserDao, PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthUser login(String login, String password) {
        AuthUser user = authUserDao.getByLogin(login);
        if (user == null){
            return null;
        }
        if (passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }
}
