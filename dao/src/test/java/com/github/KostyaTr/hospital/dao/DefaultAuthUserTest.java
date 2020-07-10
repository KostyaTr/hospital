package com.github.KostyaTr.hospital.dao;


import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultAuthUserTest {

    @Autowired
    private AuthUserDao authUserDao;

    @Autowired
    private UserDao userDao;

    @Test
    void findByLogin(){
        Long userId = userDao.saveUser(new User(null, "authCheck","authCheck","authCheck","authCheck"));

        authUserDao.saveAuthUser(new AuthUser(null, "login", "password", Role.AuthorizedUser, userId));

        assertNotNull(authUserDao.getByLogin("login"));
        assertNull(authUserDao.getByLogin("IncorrectLogin"));
    }

    @Test
    void saveAuthUser(){
        Long userId = userDao.saveUser(new User(null, "saveCheck","saveCheck","saveCheck","saveCheck"));
        final AuthUser authUser = new AuthUser(
                null, "saveCheck", "saveCheck", Role.AuthorizedUser, userId);
        assertNotNull(authUserDao.saveAuthUser(authUser));
    }
}
