package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.service.AuthorizationService;

public class DefaultAuthorizationService implements AuthorizationService {
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

    private static class SingletonHolder {
        static final AuthorizationService HOLDER_INSTANCE = new DefaultAuthorizationService();
    }

    public static AuthorizationService getInstance() {
        return DefaultAuthorizationService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public AuthUser login(String login, String password) {
        AuthUser user = authUserDao.getByLogin(login);
        if (user == null){
            return null;
        }
        if (password.equals(user.getPassword())){
            return user;
        }
        return null;
    }
}
