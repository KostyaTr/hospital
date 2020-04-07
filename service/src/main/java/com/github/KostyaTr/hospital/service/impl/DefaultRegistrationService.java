package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.service.RegistrationService;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.UserDao;

public class DefaultRegistrationService implements RegistrationService {
    private UserDao userDao = DefaultUserDao.getInstance();
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

    private static volatile RegistrationService instance;

    public static RegistrationService getInstance(){
        RegistrationService localInstance = instance;
        if (localInstance == null){
            synchronized (RegistrationService.class){
                localInstance = instance;
                if (localInstance == null){
                    localInstance = instance = new DefaultRegistrationService();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void saveAuthUser(AuthUser user) {
        authUserDao.saveAuthUser(user);
    }

    @Override
    public boolean loginCheck(String login) {
        for ( User user:userDao.getUsers()) {
            if (login.equals(user.getLogin())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean passwordCheck(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }
}
