package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;

import javax.management.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao {
    private static class SingletonHolder {
        static final UserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long saveUser(User user) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        return user.getUserId();
    }

    @Override
    public boolean removeUser(Long userId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete User where id = :id")
                .setParameter("id", userId)
                .executeUpdate();
        session.getTransaction().commit();
        return getUserById(userId) == null;
    }

    @Override
    public User getUserById(Long userId) {
       Session session = HibernateUtil.getSession();
       session.beginTransaction();
       User user = session.get(User.class, userId);
       session.getTransaction().commit();
       return user;
    }
}
