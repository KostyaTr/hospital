package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.service.AuthorizationService;

public class DefaultAuthorizationService implements AuthorizationService {
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

    private static volatile AuthorizationService instance;

    public static AuthorizationService getInstance(){
        AuthorizationService localInstance = instance;
        if (localInstance == null){
            synchronized (AuthorizationService.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DefaultAuthorizationService();
                }
            }
        }
        return localInstance;
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
