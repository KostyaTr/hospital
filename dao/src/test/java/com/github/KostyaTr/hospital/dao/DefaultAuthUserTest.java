package com.github.KostyaTr.hospital.dao;


import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DefaultAuthUserTest {
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();

    @Test
    void findByLogin(){
        User user = new User(null, "authCheck","authCheck","authCheck","authCheck", null, null, null, null, null, null);
        final AuthUser authUser = new AuthUser(null, "login", "password", Role.AuthorizedUser, null);
        user.setAuthUser(authUser);
        authUser.setUser(user);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        assertNotNull(authUserDao.getByLogin("login"));
        assertNull(authUserDao.getByLogin("IncorrectLogin"));
    }

    @Test
    void saveAuthUser(){
        User user = new User(null, "saveAuthCheck","saveAuthCheck","saveAuthCheck","saveAuthCheck", null, null, null, null, null, null);
        userDao.saveUser(user);
        final AuthUser authUser = new AuthUser(
                null, "saveCheck", "saveCheck", Role.AuthorizedUser, user);
        assertNotNull(authUserDao.saveAuthUser(authUser));
    }
}
