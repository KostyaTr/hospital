package com.github.KostyaTr.hospital.dao;


import com.github.KostyaTr.hospital.dao.entity.AuthUserEntity;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultAuthUserDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DefaultAuthUserTest {
    private AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();
    private UserDao userDao = DefaultUserDao.getInstance();

    @Test
    void findByLogin(){
        UserEntity user = new UserEntity(null, "authCheck","authCheck","authCheck","authCheck", null, null, null);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long userId = (Long) session.save(user);
        final AuthUserEntity authUser = new AuthUserEntity(null, "login", "password", Role.AuthorizedUser, userId);
        session.save(authUser);
        session.getTransaction().commit();

        assertNotNull(authUserDao.getByLogin("login"));
        assertNull(authUserDao.getByLogin("IncorrectLogin"));
    }

    @Test
    void saveAuthUser(){
        UserEntity user = new UserEntity(null, "saveCheck","saveCheck","saveCheck","saveCheck", null, null, null);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long userId = (Long) session.save(user);
        session.getTransaction().commit();
        final AuthUser authUser = new AuthUser(
                null, "saveCheck", "saveCheck", Role.AuthorizedUser, userId);
        assertNotNull(authUserDao.saveAuthUser(authUser));
    }
}
