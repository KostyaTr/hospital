package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.service.AuthorizationService;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        if (hashPassword(password).equals(user.getPassword())){
            return user;
        }
        return null;
    }

    private String hashPassword(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hashedPassword = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return hashedPassword;
    }
}
